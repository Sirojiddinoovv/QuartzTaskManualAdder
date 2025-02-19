package uz.nodir.quartztaskmanualadder.model.dto.court.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import uz.nodir.quartztaskmanualadder.model.dto.core.request.PageParams

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

data class CourtFilterRequestDTO(

    @JsonProperty("params")

    val params: CourtFilterParam
) : PageParams()

data class CourtFilterParam(
    @JsonProperty("loan_ext_id")

    @field:NotBlank
    val loanExtId: String,
)