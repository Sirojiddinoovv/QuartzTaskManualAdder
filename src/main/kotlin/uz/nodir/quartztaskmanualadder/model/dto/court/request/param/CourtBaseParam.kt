package uz.nodir.quartztaskmanualadder.model.dto.court.request.param

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

abstract class CourtBaseParam(

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @field:NotBlank open val sentDateTime: String?,

    @field:NotBlank open val message: String?,

    @field:NotBlank open val phone: String?,
)