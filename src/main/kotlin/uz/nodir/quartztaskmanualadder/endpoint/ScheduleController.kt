package uz.nodir.quartztaskmanualadder.endpoint


import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CourtFilterRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CourtUpdateRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CreateLoanCourtRequestDTO
import uz.nodir.quartztaskmanualadder.service.business.CourtService
import uz.nodir.quartztaskmanualadder.model.dto.core.response.CoreError
import uz.nodir.quartztaskmanualadder.model.dto.core.response.ResultData

/**
 * Created by Nodir
 * on Date 20 дек., 2024
 * Java Spring Boot by Davr Coders
 */

@RestController
@RequestMapping("/api/v1/schedule", produces = ["application/json"], consumes = ["application/json"])
class ScheduleController(

    private val courtService: CourtService
) {

    @PostMapping

    fun create(@RequestBody @Valid requestDTO: CreateLoanCourtRequestDTO): ResponseEntity<ResultData<CoreError?>> =
        ResponseEntity.ok(courtService.create(requestDTO))

    @PostMapping("/filter")
    fun filterCourtByLoan(@RequestBody @Valid requestDTO: CourtFilterRequestDTO) =
        courtService.filter(requestDTO)

    @PutMapping
    fun update(@RequestBody @Valid requestDTO: CourtUpdateRequestDTO) =
        courtService.update(requestDTO)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) =
        courtService.delete(id)
}