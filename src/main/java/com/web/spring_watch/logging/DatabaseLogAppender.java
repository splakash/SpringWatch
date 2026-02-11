package com.web.spring_watch.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.web.spring_watch.Entity.DTO.LogCapturedEvent;
import com.web.spring_watch.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

public class DatabaseLogAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {

        if (!eventObject.getLevel().isGreaterOrEqual(Level.ERROR)) {
            return;
        }

        String stackTrace = null;
        if (eventObject.getThrowableProxy() != null) {
            stackTrace = eventObject.getThrowableProxy().getMessage();
        }

        LogCapturedEvent event = new LogCapturedEvent(
                eventObject.getLevel().toString(),
                eventObject.getLoggerName(),
                eventObject.getFormattedMessage(),
                stackTrace,
                LocalDateTime.now()
        );

        ApplicationContext context = SpringContextHolder.getContext();
        if (context != null) {
            context.publishEvent(event);
        }

    }
}

