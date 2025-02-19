package uz.nodir.quartztaskmanualadder.model.dto.core.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

/**
 * Created by Nodir
 * on Date 26 дек., 2023
 * Java Spring Boot by Davr Coders
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class CoreError(
     val code: Int?,
     val message: String?
) {


}
