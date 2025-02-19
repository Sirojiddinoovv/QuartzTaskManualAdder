package uz.nodir.quartztaskmanualadder.model.dto.core.response

import lombok.Data

/**
 * Created by Nodir
 * on Date 08 янв., 2024
 * Java Spring Boot by Davr Coders
 */
@Data
class SimpleResponse<S> {
    var error: CoreError? = null
    var status: Boolean
    var data: S? = null
    private var params: Map<Any, Any> = HashMap(5)
    private var exception: Exception? = null
    val isTimeout = false

    constructor() {
        this.status = true
    }

    constructor(data: S) {
        this.status = true
        this.data = data
    }

    constructor(params: Map<Any, Any>, isSuccess: Boolean) {
        this.status = isSuccess
        this.params = params
    }

    constructor(id: Int?, message: String?) {
        this.status = false
        this.error = CoreError(id, message)
    }


    constructor(id: Int?, message: String?, ex: Exception?) {
        this.status = false
        this.error = CoreError(id, message)
        this.exception = ex
    }

    fun toMap(): Map<Any, Any?> {
        val entityMap = HashMap<Any, Any?>()
        entityMap["error"] = error
        entityMap["status"] = status
        entityMap["data"] = data
        entityMap["params"] = params
        return entityMap
    }
}
