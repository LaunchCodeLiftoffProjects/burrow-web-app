package com.example.burrowwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.burrowwebapp.controller.ViewController.columnChoices;

@Controller
@RequestMapping("searchbar")
public class SearchbarController {

    @Autowired
    private DeviceController deviceController;

    @GetMapping
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "searchbar/index";
    }
}
