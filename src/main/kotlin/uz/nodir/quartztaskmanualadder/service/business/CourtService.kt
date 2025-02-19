package uz.nodir.quartztaskmanualadder.service.business

import jakarta.validation.constraints.NotNull
import uz.nodir.quartztaskmanualadder.model.dto.core.response.CoreError
import uz.nodir.quartztaskmanualadder.model.dto.core.response.ResultData
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CourtFilterRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CourtUpdateRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.request.CreateLoanCourtRequestDTO
import uz.nodir.quartztaskmanualadder.model.dto.court.response.CourtFilterResponseDTO


/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */
interface CourtService {
    fun create(@NotNull requestDTO: CreateLoanCourtRequestDTO): ResultData<CoreError?>

    fun filter(@NotNull requestDTO: CourtFilterRequestDTO): ResultData<CourtFilterResponseDTO?>

    fun update(@NotNull requestDTO: CourtUpdateRequestDTO): ResultData<CoreError?>

    fun delete(@NotNull id: Long): ResultData<CoreError?>
}