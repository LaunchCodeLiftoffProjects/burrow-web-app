package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.ComponentRepository;
        import com.example.burrowwebapp.data.NotificationRepository;
        import com.example.burrowwebapp.models.Component;
        import com.example.burrowwebapp.models.Notification;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;

        import java.time.LocalDate;
        import java.util.ArrayList;

@Controller
@RequestMapping("notifications")
public class NotificationController
{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ComponentRepository componentRepository;

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

    @PostMapping
    public String resetNotifications(@RequestParam(required=false) int[] notificationIds){
        if(notificationIds != null){
            for(int id : notificationIds){
                Notification notification = notificationRepository.findById(id).get();
                notification.replaceComponent();
                Component component = notification.getComponent();
                component.setInstallDate(LocalDate.now());
                componentRepository.save(component);
                notificationRepository.save(notification);
            }
        }
        return "notifications/index";
    }
}
