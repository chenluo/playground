package com.chenluo.servicediscover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(DemoController.class);
    private final String LOGGER_PREFIX = "[Demo]";

    @Scheduled(cron = "*/30 * * * * *")
    public String heartBeat() {
        logger.info("{} heartBeat.", LOGGER_PREFIX);
        return "OK";
    }
}
