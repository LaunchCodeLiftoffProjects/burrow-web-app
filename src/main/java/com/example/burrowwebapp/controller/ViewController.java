package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.*;
import com.example.burrowwebapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "view")
public class ViewController {

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ViewController() {

        columnChoices.put("all", "All");
        columnChoices.put("property", "Property");
        columnChoices.put("room", "Room");
        columnChoices.put("device", "Device");
        columnChoices.put("component", "Component");
        columnChoices.put("component description", "Component Description");
    }

    @RequestMapping("")
    public String list(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);

        List<Room> rooms = (List<Room>) roomRepository.findAllById(Collections.singleton(userId));
        model.addAttribute("rooms", rooms);

        List<Property> properties = (List<Property>) propertyRepository.findAllById(Collections.singleton(userId));
        model.addAttribute("property", properties);

        List<Device> devices = (List<Device>) deviceRepository.findAllById(Collections.singleton(userId));
        model.addAttribute("devices", devices);

        List<Component> components = (List<Component>) componentRepository.findAllById(Collections.singleton(userId));
        model.addAttribute("components", components);

        return "view";
    }

}