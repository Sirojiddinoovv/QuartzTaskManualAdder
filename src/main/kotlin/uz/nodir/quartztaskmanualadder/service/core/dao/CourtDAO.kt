package uz.nodir.quartztaskmanualadder.service.core.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import uz.nodir.quartztaskmanualadder.model.entity.Court
import java.util.*

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */
interface CourtDAO {
    fun save(court: Court): Court

    fun findAllByLoanExtId(loanExtId: String, pageable: Pageable): Page<Court>

    fun findByJobId(jobId: UUID): Court?

    fun findById(id: Long): Court?

    fun deleteById(id: Long)
}