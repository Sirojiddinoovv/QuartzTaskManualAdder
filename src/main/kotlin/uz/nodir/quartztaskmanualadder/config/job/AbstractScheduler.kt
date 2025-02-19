package uz.nodir.quartztaskmanualadder.config.job

import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException


/**
 * Created by Nodir
 * on Date 20 дек., 2024
 * Java Spring Boot by Davr Coders
 */

abstract class AbstractScheduler: Job {

    override fun execute(context: JobExecutionContext?) {
        try {
            executeTask(context)
        } catch (e: Exception) {
            throw JobExecutionException(e)
        }
    }

    abstract fun executeTask(context: JobExecutionContext?)


    fun getJobDataMap(retryCount: Int?): JobDataMap {
        val jobDataMap = JobDataMap()
        jobDataMap.put(SchedulerConstants.CURRENT_RETRY_COUNT, 0)
        jobDataMap[SchedulerConstants.MAX_ALLOWED_RETRY_COUNT] = retryCount
        return jobDataMap
    }
}