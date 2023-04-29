package com.nuracell.bs.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ScheduledLoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledLoggingService.class);

    @Scheduled(fixedRate = 200)
    public void printTrace() {
        LOGGER.trace("Trace message");
    }

    @Scheduled(fixedRate = 300)
    public void printDebug() {
        LOGGER.debug("Degub message");
    }

    @Scheduled(fixedRate = 400)
    public void printInfo() {
        LOGGER.info("Info message");
    }

    @Scheduled(fixedRate = 500)
    public void printWarn() {
        LOGGER.warn("Warn message");
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 1)
    public void printError() {
        LOGGER.error("Error message");
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 2)
    public void throwException() {
        throw new RuntimeException("Exception");
    }
}
