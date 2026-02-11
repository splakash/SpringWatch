package com.web.spring_watch.Entity.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class LogCapturedEvent {


    //will add final later to make all immutable
    private  String level;
    private  String logger;
    private  String message;
    private  String stackTrace;
    private  LocalDateTime timestamp;

}

