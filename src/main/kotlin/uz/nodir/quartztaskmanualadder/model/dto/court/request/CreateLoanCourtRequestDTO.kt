package uz.nodir.quartztaskmanualadder.model.dto.court.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import uz.nodir.quartztaskmanualadder.model.dto.court.request.param.CourtBaseParam

/**
 * Created by Nodir
 * on Date 20 дек., 2024
 * Java Spring Boot by Davr Coders
 */

data class CreateLoanCourtRequestDTO(
    @JsonProperty("loan_ext_id")

    @field:NotBlank val loanExtId: String,

    @JsonProperty("sent_date_time")
    override val sentDateTime: String,

    @JsonProperty("message")
    override val message: String,

    @JsonProperty("phone")
    override val phone: String,

    ) : CourtBaseParam(
    sentDateTime,
    message,
    phone
)