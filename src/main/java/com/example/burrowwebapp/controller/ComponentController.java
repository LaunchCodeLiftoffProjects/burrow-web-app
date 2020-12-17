package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.ComponentRepository;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.models.Component;
import com.example.burrowwebapp.models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("components")
public class ComponentController {

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    private ArrayList<String> nameList = new ArrayList<String>() {
        {
            add("Light Bulb");
            add("Battery");
            add("Filter");
        }
    };

    @GetMapping
    public String displayAllComponents(Model model) {
        model.addAttribute("components", componentRepository.findAll());
        return "components/index";
    }

    @GetMapping("add")
    public String displayAddComponentForm(Model model, @RequestParam int deviceId) {
        model.addAttribute(new Component());
        model.addAttribute("device", deviceRepository.findById(deviceId));
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("properties", propertyRepository.findAll());
        model.addAttribute("components", componentRepository.findAll());
        model.addAttribute("names", nameList);
        return "components/add";
    }
    @PostMapping("add")
    public String processAddComponentForm(@Valid @ModelAttribute Component newComponent,
                                       Errors errors, Model model, @RequestParam int deviceId) {
        if (errors.hasErrors()) {
            model.addAttribute("devices", deviceRepository.findAll());
            return "components/add";
        }
        Device device = deviceRepository.findById(deviceId).get();
        newComponent.setDevice(device);
        componentRepository.save(newComponent);
        return "redirect:";
    }

    @GetMapping(path = {"view/{componentId}", "view"})
    public String displayViewComponent(Model model, @PathVariable (required = false) Integer componentId) {
        if (componentId == null) {
            model.addAttribute("components", componentRepository.findAll());
            return "components/index";
        } else {
            Optional<Component> result = componentRepository.findById(componentId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Component component = result.get();
                model.addAttribute("component", component);
            }
        }
        return "components/view";
    }

}
