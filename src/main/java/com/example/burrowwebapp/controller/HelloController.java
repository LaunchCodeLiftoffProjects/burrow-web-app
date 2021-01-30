package com.example.burrowwebapp.controller;

/**
 *Hello World Page Created by Kaitlyn Forks
 */

import com.example.burrowwebapp.data.NotificationRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.Notification;
import com.example.burrowwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private NotificationRepository notificationRepository;

    private static final String userSessionKey = "user";

    @RequestMapping("")
    public String welcomeUserFromSession(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }
        Iterable<Notification> allNotifications = notificationRepository.findAll();
        ArrayList<Notification> activeNotifications = new ArrayList<>();
        for(Notification notification: allNotifications){
            notification.needsToBeReplaced();
            if(notification.isActive() && notification.getUser().getId()==userId){
                activeNotifications.add(notification);
            }
        }
        model.addAttribute("notifications", activeNotifications);
        model.addAttribute("user", user.get());
        return "index";
    }
}
