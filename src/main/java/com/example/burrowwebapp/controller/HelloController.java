package com.example.burrowwebapp.controller;

/**
 *Hello World Page Created by Kaitlyn Forks
 */

import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    @RequestMapping("")
    public String getUserFromSession(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }
        model.addAttribute("user", user.get());
        return "index";
    }

//    public String index(Model model) {
//        model.addAttribute("properties", propertyRepository.findAll());
//        return "index";
//    }
}
