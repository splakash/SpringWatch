package com.web.spring_watch.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dummyController {

    @GetMapping("/")
        public String hello(){
            return "Hello";
        }
}
