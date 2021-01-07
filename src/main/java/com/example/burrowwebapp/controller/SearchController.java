
package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private DeviceRepository deviceRepository;

    @RequestMapping("")
    public String search(Model model) {


        return "search";
    }


}