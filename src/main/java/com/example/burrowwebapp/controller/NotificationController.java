package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.NotificationRepository;
import com.example.burrowwebapp.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("notifications")
public class NotificationController
{
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public String displayNotifications(Model model){
        Iterable<Notification> allNotifications = notificationRepository.findAll();
        ArrayList<Notification> activeNotifications = new ArrayList<>();
        for(Notification notification: allNotifications){
            notification.needsToBeReplaced();
            if(notification.isActive()){
                activeNotifications.add(notification);
            }
        }
        model.addAttribute("notifications", activeNotifications);
        return "notifications/index";
    }
}
