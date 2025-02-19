package uz.nodir.quartztaskmanualadder.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import uz.nodir.quartztaskmanualadder.model.dto.core.request.PageParams

/**
 * Created by Nodir
 * on Date 22 янв., 2024
 * Java Spring Boot by Davr Coders
 */
object PageUtils {
    fun pageableBuilder(params: PageParams): Pageable {
        return if (params.descending) PageRequest.of(
            params.pageNumber,
            params.pageSize,
            Sort.Direction.DESC,
            params.sortBy
        )
        else PageRequest.of(
            params.pageNumber,
            params.pageSize,
            Sort.Direction.ASC,
            params.sortBy
        )
    }
}
