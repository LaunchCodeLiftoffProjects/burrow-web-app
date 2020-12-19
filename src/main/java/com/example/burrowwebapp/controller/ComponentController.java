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
    public String displayAddComponentForm(Model model) {
        model.addAttribute(new Component());
        model.addAttribute("devices", deviceRepository.findAll());
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

    @GetMapping("edit/{componentId}")
    public String displayEditComponent(Model model, @PathVariable int componentId) {
        Component component = componentRepository.findById(componentId).get();
        model.addAttribute("component", component);
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("properties", propertyRepository.findAll());
        model.addAttribute("names", nameList);
        return "components/edit";
    }

    @PostMapping("edit")
    public String processEditComponent(int componentId, String name, @RequestParam int deviceId, String type, @RequestParam int quantity, String description) {
        Component component = componentRepository.findById(componentId).get();
        Device device = deviceRepository.findById(deviceId).get();
        component.setName(name);
        component.setDevice(device);
        component.setType(type);
        component.setQuantity(quantity);
        component.setDescription(description);
        componentRepository.save(component);
        return "redirect:";
    }



    @GetMapping("delete")
    public String displayDeleteComponent(Model model) {
        model.addAttribute("title", "Delete Components");
        model.addAttribute("components", componentRepository.findAll());
        return "components/delete";
    }

    @PostMapping("delete")
    public String processDeleteComponent(@RequestParam(required = false) int[] componentId) {
        if (componentId != null) {
            for (int id : componentId) {
                componentRepository.deleteById(id);
            }
        }
        return "redirect:";
    }
}
