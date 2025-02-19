package uz.nodir.quartztaskmanualadder.service.business.component

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Created by Nodir
 * on Date 23 дек., 2024
 * Java Spring Boot by Davr Coders
 */

@Component
class CourtAlertComponent {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java);
    }

    fun sendNotification(loanExtId: String, phone: String, message: String) {
        log.info("Start sending alert notification to broker: {} {} {}", loanExtId, phone, message)

        // toDo some logic
        /*
            I'm used kafka producer to send notification and take it from another service
         */

        log.info("End broker result")
    }
}