package uz.nodir.quartztaskmanualadder.service.business

import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import uz.nodir.quartztaskmanualadder.service.SchedulerService
import uz.nodir.quartztaskmanualadder.service.createSimpleTrigger
import uz.nodir.quartztaskmanualadder.config.job.AbstractScheduler
import uz.nodir.quartztaskmanualadder.model.dto.core.response.CoreError
import uz.nodir.quartztaskmanualadder.model.dto.core.response.SimpleResponse
import uz.nodir.quartztaskmanualadder.service.business.component.CourtAlertComponent
import uz.nodir.quartztaskmanualadder.service.core.dao.CourtDAO
import java.util.*


/**
 * Created by Nodir
 * on Date 20 дек., 2024
 * Java Spring Boot by Davr Coders
 */

@Service
class CourtDateScheduleService(
    val schedulerService: SchedulerService,
    val courtAlertComponent: CourtAlertComponent,
    val courtDAO: CourtDAO,
) : AbstractScheduler() {

    companion object {
        const val RETRY_COUNT = 0
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun executeTask(context: JobExecutionContext?) {
        val jobDataMap = context!!.trigger.jobDataMap
        val message = jobDataMap.getString("message")
        val date = jobDataMap["date"]
        val loanExtId = jobDataMap["loanExtId"] as String?
        val phone = jobDataMap["phone"] as String?
        val id = jobDataMap["id"] as UUID?

        log.info("Scheduled job started at: $date with data: $message $date $loanExtId")

        send(id, loanExtId, phone, message)
    }


    fun scheduleCourtDay(
        message: String,
        date: Date,
        loanExtId: String,
        phone: String,
        id: UUID
    ): SimpleResponse<CoreError> {
        log.info("Build scheduler court by loan: $loanExtId in $date")


        val jobDataMap = getJobDataMap(RETRY_COUNT)
        jobDataMap["message"] = message
        jobDataMap["date"] = date
        jobDataMap["loanExtId"] = loanExtId
        jobDataMap["phone"] = phone
        jobDataMap["id"] = id


        val jobDetail: JobDetail = schedulerService.buildJobDetail(this.javaClass, jobDataMap)

        val trigger = createSimpleTrigger(
            jobDetail, id.toString(),
            jobDataMap, date
        )

        return schedulerService.schedule(jobDetail, trigger!!)
    }


    fun send(id: UUID?, loanExtID: String?, phone: String?, message: String?) {
        log.info("Start executing alert for id: {}", id)

        listOf(id, loanExtID, phone, message)
            .takeIf {
                it.all { param -> param != null }
            }?.let {

                val court = courtDAO.findByJobId(id!!)

                court?.let {
                    courtAlertComponent.sendNotification(
                        loanExtId = loanExtID!!,
                        message = message!!,
                        phone = phone!!
                    )
                } ?: {
                    "Court not found and not sent alert for id: $id"
                }

            } ?: log.error("Method finished with error: \"One or more parameters are null\"")

    }

    fun rescheduleCourtDay(
        message: String?,
        date: Date?,
        phone: String?,
        id: UUID?
    ): SimpleResponse<CoreError> {
        log.info("Reschedule court by id: $id in $date")

        listOf(id, date, phone, message)
            .takeIf {
                it.all { param -> param != null }
            }?.let {
                return schedulerService.reschedule(id!!, date!!, message!!, phone!!)
            } ?: {
            log.error("One or more parameters are null")
        }

        return SimpleResponse(500, "Wrong Params")
    }

    fun deleteScheduleCourtDay(
        id: UUID?
    ): SimpleResponse<CoreError> {
        log.info("Delete schedule court by id: $id")

        id ?: return SimpleResponse(500, "Wrong params")

        id.let {
            return schedulerService.deleteSchedule(id)
        }
    }
}