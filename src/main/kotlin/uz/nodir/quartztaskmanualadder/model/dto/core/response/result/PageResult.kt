package uz.nodir.quartztaskmanualadder.model.dto.core.response.result

/**
 * Created by Nodir
 * on Date 23 янв., 2024
 * Java Spring Boot by Davr Coders
 */
abstract class PageResult {
     var size = 0
     var currentPage = 0
     var numberOfElements = 0
     var totalPages = 0
     var totalElements: Long = 0
     var isLast = false
     var isFirst = false
}
