package com.web.spring_watch.Repository;

import com.web.spring_watch.Entity.logEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEventRepo extends JpaRepository<logEvent, Long> {
}
