package com.web.spring_watch;

import com.web.spring_watch.Entity.DTO.LogCapturedEvent;
import com.web.spring_watch.Entity.logEvent;
import com.web.spring_watch.Repository.LogEventRepo;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogEventListener {

    private final LogEventRepo repository;

    public LogEventListener(LogEventRepo repository) {
        this.repository = repository;
    }

    @EventListener
    @Async
    public void handleLogCapturedEvent(LogCapturedEvent event) {

        logEvent log = new logEvent();
        log.setLevel(event.getLevel());
        log.setLogger(event.getLogger());
        log.setMessage(event.getMessage());
        log.setStackTrace(event.getStackTrace());
        log.setTimestamp(event.getTimestamp());

        repository.save(log);
    }
}

