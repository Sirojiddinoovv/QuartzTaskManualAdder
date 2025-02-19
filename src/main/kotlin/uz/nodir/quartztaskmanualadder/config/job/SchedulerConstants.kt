package uz.nodir.quartztaskmanualadder.config.job

/**
 * Created by Nodir
 * on Date 20 дек., 2024
 * Java Spring Boot by Davr Coders
 */
class SchedulerConstants {
    companion object {
        const val RETRIES = "retries";
        const val MAX_ALLOWED_RETRY_COUNT = "max_allowed_retry_count";
        const val CURRENT_RETRY_COUNT = "current_retry_count";
        const val RETRY_DELAY = "retry_delay";

        const val MANUAL_ADDED_GROUP_ID = "courtServiceManual"
    }
}