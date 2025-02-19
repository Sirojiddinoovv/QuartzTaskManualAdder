@file:JvmName("Scheduler Service") //if set name same with class, we take a compilation error
package uz.nodir.quartztaskmanualadder.service

import org.quartz.*
import org.quartz.impl.matchers.GroupMatcher
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean
import org.springframework.stereotype.Service
import uz.nodir.quartztaskmanualadder.config.job.JobHandlerListener
import uz.nodir.quartztaskmanualadder.config.job.SchedulerConstants.Companion.MANUAL_ADDED_GROUP_ID
import uz.nodir.quartztaskmanualadder.model.dto.core.response.CoreError
import uz.nodir.quartztaskmanualadder.model.dto.core.response.SimpleResponse
import uz.nodir.quartztaskmanualadder.model.enums.ResponseStatus
import java.util.*


/**
 * Created by Nodir
 * on Date 20 дек., 2024
 * Java Spring Boot by Davr Coders
 */
@JvmName("createSimpleTrigger")
fun createSimpleTrigger(
    jobDetail: JobDetail?,
    triggerName: String?,
    map: JobDataMap?,
    date: Date
): SimpleTrigger? {
    val factoryBean = SimpleTriggerFactoryBean()
    factoryBean.setJobDetail(jobDetail!!)
    factoryBean.jobDataMap = map!!
    factoryBean.setStartTime(date)
    factoryBean.setRepeatCount(0)
    factoryBean.setGroup(MANUAL_ADDED_GROUP_ID)

    factoryBean.setName(triggerName!!)
    // in case of misfire, ignore all missed triggers and continue :
    factoryBean.setMisfireInstruction(
        SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW
    )
    factoryBean.afterPropertiesSet()
    return factoryBean.getObject()
}

@Service
class SchedulerService(
    @Autowired private val schedulerFactoryBean: SchedulerFactoryBean,
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    fun schedule(jobDetail: JobDetail, trigger: Trigger): SimpleResponse<CoreError> {
        log.info("Scheduling job: {} with trigger: {}", jobDetail.toString(), trigger.toString())
        try {
            val scheduler = schedulerFactoryBean.scheduler
            scheduler.listenerManager.addJobListener(
                JobHandlerListener(),
                GroupMatcher.groupEquals(MANUAL_ADDED_GROUP_ID)
            )
            scheduler.scheduleJob(jobDetail, trigger)

            log.info("Job scheduled!")
            return SimpleResponse(
                CoreError(
                    0,
                    ResponseStatus.SUCCESS.name
                )
            )
        } catch (e: SchedulerException) {
            log.error("Scheduler exception", e)
            return SimpleResponse(
              500,
                e.message
            )
        }
    }


    fun reschedule(jobId: UUID, newDate: Date, message: String, phone: String): SimpleResponse<CoreError> {
        log.info("Rescheduling trigger by id: {}", jobId)
        try {
            val scheduler = schedulerFactoryBean.scheduler

            val triggers = scheduler.getTriggerKeys(
                GroupMatcher.groupEquals(MANUAL_ADDED_GROUP_ID)
            )

            val triggerResult = triggers
                .firstOrNull { trigger -> trigger.name.equals(jobId.toString()) }
                ?: return SimpleResponse(404, "NOT FOUND")




            triggerResult.let {
                val newTrigger = scheduler.getTrigger(it)
                    .triggerBuilder
                    .usingJobData("message", message)
                    .usingJobData("phone", phone)
                    .startAt(newDate)
                    .build()

                scheduler.rescheduleJob(it, newTrigger)
            }



            return SimpleResponse(
                CoreError(
                    0,
                    ResponseStatus.SUCCESS.name
                )
            )
        } catch (e: SchedulerException) {
            log.error("Scheduler exception", e)
            return SimpleResponse(
                500,
                e.message
            )
        }
    }

    fun buildJobDetail(
        jobClass: Class<out Job?>?,
        jobDataMap: JobDataMap?,

        ): JobDetail {
        return JobBuilder.newJob(jobClass)
            .usingJobData(jobDataMap)
            .storeDurably(true)
            .build()
    }


    fun deleteSchedule(jobId: UUID): SimpleResponse<CoreError> {
        log.info("Delete scheduled trigger by id: {}", jobId)
        try {
            val scheduler = schedulerFactoryBean.scheduler

            val triggers = scheduler.getTriggerKeys(
                GroupMatcher.groupEquals(MANUAL_ADDED_GROUP_ID)
            )

            val triggerResult = triggers
                .firstOrNull { trigger -> trigger.name.equals(jobId.toString()) }
                ?: return SimpleResponse(404, "NOT FOUND")



            triggerResult.let {
                scheduler.unscheduleJob(it)
            }


            return SimpleResponse(
                CoreError(
                    0,
                    ResponseStatus.SUCCESS.name
                )
            )
        } catch (e: SchedulerException) {
            log.error("Scheduler exception", e)
            return SimpleResponse(
                500,
                e.message
            )
        }
    }


}