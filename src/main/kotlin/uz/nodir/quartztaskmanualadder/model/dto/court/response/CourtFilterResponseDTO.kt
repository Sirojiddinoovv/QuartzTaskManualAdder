package uz.nodir.quartztaskmanualadder.model.dto.court.response

import uz.nodir.quartztaskmanualadder.model.dto.core.response.result.PageResult
import uz.nodir.quartztaskmanualadder.model.dto.court.response.result.CourtBaseResult

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

class CourtFilterResponseDTO : PageResult() {
    var courts: List<CourtBaseResult> = emptyList()


}