package com.chenluo.config;

import io.sentry.Sentry;
import io.sentry.log4j2.SentryAppender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class SentryConfig {
    @Value("${sentry.dsn}")
    private String dsn;

    @Value("${sentry.logging}")
    private boolean useLogging;

    @EventListener(ApplicationReadyEvent.class)
    public void initSentry() {
        System.out.println("start configure sentry");
        Sentry.init(options -> {
            options.setDsn(dsn);
            // Set tracesSampleRate to 1.0 to capture 100% of transactions for performance
            // monitoring.
            // We recommend adjusting this value in production.
            options.setTracesSampleRate(1.0);
            // When first trying Sentry it's good to see what the SDK is doing:
            options.setDebug(true);
        });
        if (useLogging) {
            LoggerContext context = (LoggerContext) LogManager.getContext();
            boolean present = context.getConfiguration().getLoggers().values().stream().anyMatch(
                    l -> l.getAppenders().values().stream()
                            .anyMatch(ap -> ap instanceof SentryAppender));
            if (!present) {
                SentryAppender sentryAppender =
                        SentryAppender.createAppender("sentryAppender", Level.INFO, Level.INFO, dsn,
                                true, null, null);
                sentryAppender.start();
                context.getConfiguration().getLoggers().values().forEach(
                        loggerConfig -> loggerConfig.addAppender(sentryAppender, Level.INFO, null));
            }
        }
    }

}
