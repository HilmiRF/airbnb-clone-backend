package com.hilmirafiff.airbnb_clone_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello Endpoint", description = "Endpoints for Hello World")
@RequestMapping("hello")
public class HelloController {

    @GetMapping("/world")
    public String hello(){
        return "Hello World!";
    }
}
