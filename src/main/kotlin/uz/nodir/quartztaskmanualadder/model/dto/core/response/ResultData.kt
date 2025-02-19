package uz.nodir.quartztaskmanualadder.model.dto.core.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.NotNull
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString
import uz.nodir.quartztaskmanualadder.model.enums.ResponseStatus

/**
 * Created by Nodir
 * on Date 27 дек., 2023
 * Java Spring Boot by Davr Coders
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
class ResultData<R> {
    private var successCode: Int
    private var isSuccess: Boolean
    private var status: ResponseStatus
    private var result: R? = null
    private var error: CoreError? = null
    private val timeStamp = System.currentTimeMillis()

    constructor(result: R) {
        this.successCode = 0
        this.isSuccess = true
        this.status = ResponseStatus.SUCCESS
        this.result = result
    }

    constructor(code: Int?, e: Exception) {
        this.successCode = -1
        this.isSuccess = false
        this.status = ResponseStatus.FAILED
        this.error = CoreError(code, e.message)
    }

    constructor(code: Int?, message: String?) {
        this.successCode = -1
        this.isSuccess = false
        this.status = ResponseStatus.FAILED
        this.error = CoreError(code, message)
    }


    constructor(coreError: CoreError?, isSuccess: Boolean?) {
        this.successCode = -1
        this.isSuccess = false
        this.status = ResponseStatus.FAILED
        this.error = coreError
    }

    fun toMap(): Map<Any, Any?> {
        val map = HashMap<Any, Any?>()
        map["successCode"] = successCode
        map["isSuccess"] = isSuccess
        map["status"] = status

        if (this.result != null) map["result"] = result
        if (this.error != null) map["error"] = error

        return map
    }

    fun errorToJson(): String {
        return """{
                  "successCode": ${successCode},
                  "isSuccess": ${isSuccess},
                  "status": "${status}",
                  "error": {
                      "code": ${error!!.code},
                      "message": "${error!!.message}"
                           },
                  "timeStamp": ${timeStamp}
                  }"""
    }

    companion object {
        fun <T> ok(body: @NotNull T?): ResultData<T?> {
            return ResultData(body)
        }
    }
}
