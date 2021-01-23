package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.HomeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.burrowwebapp.controller.ViewController.columnChoices;

@Controller
@RequestMapping("searchbar")
public class SearchbarController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "searchbar/index";
    }

    @PostMapping("")
    public String displaySearchResults(Model model, @RequestParam String searchTerm){
        Iterable<Device> devices;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            devices = deviceRepository.findAll();
        } else {
            devices = HomeData.findByValue(searchTerm, deviceRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("devices", devices);
        model.addAttribute("result", ": " + searchTerm);

        return "searchbar/index";
    }
}
