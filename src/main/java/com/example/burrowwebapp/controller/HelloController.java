package com.example.burrowwebapp.controller;

/**
 *Hello World Page Created by Kaitlyn Forks
 */

import com.example.burrowwebapp.data.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /**
     *We won't need Response body annotation when we apply templates
     */

//    @GetMapping
//    @ResponseBody
//    public String hello(){
//        return "Hello Burrow!";
//    }

    @Autowired
    private PropertyRepository propertyRepository;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        return "index";
    }
}
