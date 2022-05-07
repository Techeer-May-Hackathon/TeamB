package com.techeer.svproject.domain.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.techeer.svproject.global.utils.Constants.API_PREFIX;


@RestController
public class HelloController {

    @GetMapping(API_PREFIX +"/hello")
    public String hello() { return "hello";}
    
}
