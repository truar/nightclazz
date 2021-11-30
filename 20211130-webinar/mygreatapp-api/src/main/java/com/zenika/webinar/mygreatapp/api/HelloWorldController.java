package com.zenika.webinar.mygreatapp.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping
    public String readAllMessages() {
        return "Hello from Cloud Run !";
    }

}
