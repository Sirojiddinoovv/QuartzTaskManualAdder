package uz.nodir.quartztaskmanualadder.utils

import uz.nodir.quartztaskmanualadder.model.dto.core.response.SimpleResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Nodir
 * on Date 12 дек., 2023
 * Java Spring Boot by Davr Coders
 */


object DateUtils {

    val dateTimeNow: LocalDateTime
        get() = LocalDateTime.now(ZoneId.of("Asia/Tashkent"))

    val dateNow: LocalDate
        get() = LocalDate.now(ZoneId.of("Asia/Tashkent"))

    //dd.MM.yyyy -> pattern format
    fun ddMMyyyyRegex(): String {
        return "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
    }

    fun strToLocalDate(dateStr: String, format: String): SimpleResponse<LocalDate> {
        val dateFormatter = DateTimeFormatter.ofPattern(format)
        try {
            val parsedDate = LocalDate.parse(dateStr, dateFormatter)
            return SimpleResponse(parsedDate)
        } catch (e: Exception) {
            return SimpleResponse(400, e.message)
        }
    }

    fun strToDate(date: String?, format: String): SimpleResponse<Date> {
        val formatter = SimpleDateFormat(format)
        return try {
            SimpleResponse(formatter.parse(date))
        } catch (e: ParseException) {
            SimpleResponse(400, e.message)
        }
    }

    fun dateToLocalDate(date: Date): LocalDateTime {
        return date.toInstant()
            .atZone(ZoneId.of("Asia/Tashkent"))
            .toLocalDateTime()
    }
}
