package com.web.spring_watch.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class logEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;
    private String logger;

    @Column(length = 5000)
    private String message;

    @Column(length = 10000)
    private String stackTrace;

    private LocalDateTime timestamp;
}
