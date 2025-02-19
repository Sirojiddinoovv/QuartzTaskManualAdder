package uz.nodir.quartztaskmanualadder.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uz.nodir.quartztaskmanualadder.model.entity.Court
import java.util.*

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */
@Repository
interface CourtRepository : JpaRepository<Court, Long> {

    fun findAllByLoanExtID(loanExtId: String, pageable: Pageable) : Page<Court>

    fun findCourtByJobId(jobId: UUID) : Court?
}