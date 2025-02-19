package uz.nodir.quartztaskmanualadder.model.dto.court.response.result

import com.fasterxml.jackson.annotation.JsonProperty
import uz.nodir.quartztaskmanualadder.model.entity.Court
import java.time.LocalDateTime

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

data class CourtBaseResult(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("loan_ext_id")
    val loanExtId: String,

    @JsonProperty("executed_date")
    val executedDate: LocalDateTime,

    @JsonProperty("phone")
    val phone: String,

    @JsonProperty("message")
    val message: String,
) {
    constructor(court: Court) : this(
        id = court.id!!,
        loanExtId = court.loanExtID!!,
        executedDate = court.executeDate!!,
        phone = court.phone!!,
        message = court.message!!
    )
}