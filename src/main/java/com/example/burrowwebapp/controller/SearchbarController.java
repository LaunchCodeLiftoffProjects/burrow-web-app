package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.HomeData;
import com.example.burrowwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import java.util.Collections;

import static com.example.burrowwebapp.controller.ViewController.columnChoices;

@Controller
@RequestMapping("searchbar")
public class SearchbarController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "searchbar/index";
    }

    @PostMapping("")
    public String displaySearchResults(Model model, @RequestParam String searchTerm, HttpSession session){
        Iterable<Device> devices;
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            devices = deviceRepository.findAllById(Collections.singleton(userId));
        } else {
            devices = HomeData.findByValue(searchTerm, deviceRepository.findAllById(Collections.singleton(userId)));
        }
        model.addAttribute("user", user);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("users", devices);
        model.addAttribute("result", ": " + searchTerm);

        return "searchbar/index";
    }
}
