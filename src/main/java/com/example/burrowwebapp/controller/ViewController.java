package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.ComponentRepository;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.HomeData;
import com.example.burrowwebapp.models.Property;
import com.example.burrowwebapp.models.Component;
import com.example.burrowwebapp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ViewController() {

        columnChoices.put("all", "All");
        columnChoices.put("property", "Property");
        columnChoices.put("room", "Room");
        columnChoices.put("device", "Device");
        columnChoices.put("component", "Component");
    }

    @RequestMapping("")
    public String list(Model model) {
        List<Room> rooms = (List<Room>) roomRepository.findAll();
        model.addAttribute("rooms", rooms);

        List<Property> properties = (List<Property>) propertyRepository.findAll();
        model.addAttribute("property", properties);

        List<Device> devices = (List<Device>) deviceRepository.findAll();
        model.addAttribute("devices", devices);

        List<Component> components = (List<Component>) componentRepository.findAll();
        model.addAttribute("components", components);

        return "view";
    }

//    @RequestMapping(value = "devices")
//    public String listDevicesByColumnAndValue(Model model, @RequestParam String column, @RequestParam String value) {
//        Iterable<Device> devices;
//        if (column.toLowerCase().equals("all")) {
//            devices = deviceRepository.findAll();
//            model.addAttribute("title", "All Devices");
//        } else {
//            devices = HomeData.findByColumnAndValue(column, value, deviceRepository.findAll());
//            model.addAttribute("title", "Devices with " + columnChoices.get(column) + ": " + value);
//        }
//        model.addAttribute("devices", devices);
//
//        return "view-devices";
//    }
}