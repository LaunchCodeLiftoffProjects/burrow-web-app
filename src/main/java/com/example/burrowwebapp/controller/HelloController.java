package com.example.burrowwebapp.controller;

/**
 *Hello World Page Created by Kaitlyn Forks
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /**
     *We won't need Response body annotation when we apply templates
     */

    @GetMapping
    @ResponseBody
    public String hello(){
        return "Hello Burrow!";
    }

}
