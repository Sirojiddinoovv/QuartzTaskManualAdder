package uz.nodir.quartztaskmanualadder.service.business.impl

import jakarta.validation.constraints.NotNull
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import uz.nodir.quartztaskmanualadder.model.dto.core.response.CoreError
import uz.nodir.quartztaskmanualadder.model.dto.core.response.ResultData
import uz.nodir.quartztaskmanualadder.model.dto.core.response.SimpleResponse
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CourtFilterRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CourtUpdateRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CreateLoanCourtRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.response.CourtFilterResponseDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.response.result.CourtBaseResult
import uz.nodir.quartztaskmanualadder.model.entity.Court
import uz.nodir.quartztaskmanualadder.model.enums.ResponseStatus
import uz.nodir.quartztaskmanualadder.service.business.CourtDateScheduleService
import uz.nodir.quartztaskmanualadder.service.business.CourtService
import uz.nodir.quartztaskmanualadder.service.core.dao.CourtDAO
import uz.nodir.quartztaskmanualadder.utils.DateUtils
import uz.nodir.quartztaskmanualadder.utils.PageUtils
import java.util.*

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

@Service
class CourtServiceImpl(
    val courtDateScheduleService: CourtDateScheduleService,
    val courtDAO: CourtDAO
) : CourtService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }


    override fun filter(@NotNull requestDTO: CourtFilterRequestDTO): ResultData<CourtFilterResponseDTO?> {
        log.info("Received command to find courts with request DTO: {}", requestDTO)

        val pageRequest = PageUtils.pageableBuilder(requestDTO)

        val pageResult = courtDAO.findAllByLoanExtId(requestDTO.params.loanExtId, pageRequest)

        val result = CourtFilterResponseDTO()
            .apply {
                size = pageResult.size
                currentPage = pageResult.number + 1
                numberOfElements = pageResult.numberOfElements
                totalPages = pageResult.totalPages
                totalElements = pageResult.totalElements
                isLast = pageResult.isLast
                isFirst = pageResult.isFirst
                courts = pageResult.content
                    .map { CourtBaseResult(it) }
            }

        log.info("Finished command to find courts with size: {}", result.size)
        return ResultData.ok(result);
    }


    override fun create(@NotNull requestDTO: CreateLoanCourtRequestDTO): ResultData<CoreError?> {
        log.info("Received command to create court day with request dto: $requestDTO")

        val dateResult = validateDate(requestDTO.sentDateTime)

        if (!dateResult.status)
            return ResultData(dateResult.error, false)

        val date = dateResult.data

        val triggerName: UUID = UUID.randomUUID()
        log.info("Generated trigger name: {}", triggerName)


        val scheduleResult = courtDateScheduleService.scheduleCourtDay(
            requestDTO.message,
            date!!,
            requestDTO.loanExtId,
            requestDTO.phone,
            triggerName
        )

        if (!scheduleResult.status) {
            log.error("Method finished with error message: {}", scheduleResult.error?.message)
            return ResultData(scheduleResult.error, false)
        }


        courtDAO.save(
            Court(
                loanExtID = requestDTO.loanExtId,
                phone = requestDTO.phone,
                executeDate = DateUtils.dateToLocalDate(date),
                message = requestDTO.message,
                jobId = triggerName
            )
        )

        val resultData = ResultData.ok(
            CoreError(
                0,
                ResponseStatus.SUCCESS.name
            )
        )

        log.info("Finished command to create court day with result dto: $resultData")
        return resultData
    }


    fun validateDate(sentDateTime: String?): SimpleResponse<Date> {
        val dateResult = DateUtils.strToDate(sentDateTime, "dd.MM.yyyy HH:mm:ss")

        if (!dateResult.status) {
            log.error("Date parsing failed with message: ${dateResult.error}")
            return SimpleResponse(
                dateResult.error?.code,
                dateResult.error?.message
            )
        }

        val date = dateResult.data

        val now = Date()

        if (date != null) {
            if (date.before(now)) {
                val message = "Date: $date can't be before now: $now"
                log.error(message)
                return SimpleResponse(
                    400,
                    message
                )
            }
        }

        return SimpleResponse(date!!)
    }

    override fun update(@NotNull requestDTO: CourtUpdateRequestDTO): ResultData<CoreError?> {
        log.info("Received command to update court day with request DTO: {}", requestDTO)

        val dateResult = validateDate(requestDTO.sentDateTime)

        if (!dateResult.status)
            return ResultData(dateResult.error, false)

        val date = dateResult.data

        val court = courtDAO.findById(
            requestDTO.id
        ) ?: return ResultData(404, "NOT FOUND")


        court.let {
            val rescheduleResult = courtDateScheduleService.rescheduleCourtDay(
                id = it.jobId,
                message = requestDTO.message,
                date = date,
                phone = requestDTO.phone
            )

            if (!rescheduleResult.status) {
                log.error("Method finished with error message: {}", rescheduleResult.error?.message)
                return ResultData(
                    rescheduleResult.error, false
                )
            }
        }

        updateCourt(court, requestDTO, date!!)

        val result = ResultData.ok(
            CoreError(
                0,
                ResponseStatus.SUCCESS.name
            )
        )

        log.info("Finished command to update court day with result dto: $result")
        return result
    }

    private fun updateCourt(
        court: Court,
        requestDTO: CourtUpdateRequestDTO,
        date: Date
    ) {
        court.message = requestDTO.message
        court.phone = requestDTO.phone
        court.executeDate = DateUtils.dateToLocalDate(date)
        courtDAO.save(court)
    }

    override fun delete(@NotNull id: Long): ResultData<CoreError?> {
        log.info("Received command to delete court day by id: {}", id)

        val court =
            courtDAO.findById(id) ?: return ResultData(
                404, "NOT FOUND"
            )


        val now = DateUtils.dateTimeNow

        if (court.executeDate!!.isBefore(now)) {
            val message = "Court execution date: ${court.executeDate} before now: $now. Message already sent!"
            log.error(message)
            return ResultData(
                400,
                message
            )
        }

        val deleteResult = courtDateScheduleService.deleteScheduleCourtDay(court.jobId)

        if (!deleteResult.status) {
            log.error("Method finished with error message: {}", deleteResult.error?.message)
            return ResultData(deleteResult.error, false)
        }

        court.id?.let { courtDAO.deleteById(it) }

        val result = ResultData.ok(
            CoreError(
                0,
                ResponseStatus.SUCCESS.name
            )
        )

        log.info("Finished command to delete court day with result dto: $result")
        return result
    }

}