package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.HomeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import java.util.Collections;

import static com.example.burrowwebapp.controller.ViewController.columnChoices;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private DeviceRepository deviceRepository;

    private static final String userSessionKey = "user";

    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Device> devices;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            devices = deviceRepository.findAll();
        } else {
            devices = HomeData.findByColumnAndValue(searchType, searchTerm, deviceRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Gophers found " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("devices", devices);
        model.addAttribute("result", " " + columnChoices.get(searchType) + ": " + searchTerm);

        return "search";
    }
}
