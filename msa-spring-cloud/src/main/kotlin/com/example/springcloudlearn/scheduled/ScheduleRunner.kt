package com.chenluo.scheduled

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Component
class ScheduleRunner {
    val logger: Logger = LoggerFactory.getLogger(ScheduleRunner::class.java)

    @Scheduled(cron = "*/1 * * * * *")
    fun logTest() {
        val stopWatch = StopWatch()
        stopWatch.start()
        for (i in 0 until 1000) {
            logger.info("just a log")
        }
        stopWatch.stop()
        println(stopWatch.shortSummary())
    }
}