package uz.nodir.quartztaskmanualadder.model.dto.court.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import org.jetbrains.annotations.NotNull
import uz.nodir.quartztaskmanualadder.model.dto.court.request.param.CourtBaseParam

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

class CourtUpdateRequestDTO(
    @JsonProperty("id")
    @field:NotNull
    @field:Min(1)
    val id: Long = 0L,

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