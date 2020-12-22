package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.ComponentRepository;
import com.example.burrowwebapp.data.DeviceRepository;
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
public class ComponentController
{
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private ArrayList<String> nameList = new ArrayList<String>() {
        {
            add("Light Bulb");
            add("Battery");
            add("Filter");
        }
    };

    @GetMapping
    public String displayAllDevices(Model model){
        model.addAttribute("components", componentRepository.findAll());
        return "components/index";
    }

    @GetMapping("add")
    public String displayAddComponentForm(Model model){
        model.addAttribute(new Component());
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("names", nameList);
        return "components/add";
    }

    @PostMapping("add")
    public String processAddComponentForm(@Valid @ModelAttribute Component newComponent,
                                          Errors errors, Model model, @RequestParam int deviceId){
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
    public String displayEditComponentForm(Model model, @PathVariable int componentId) {
        Component component = componentRepository.findById(componentId).get();
        model.addAttribute("component", component);
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("names", nameList);
        return "components/edit";
    }

    @PostMapping("edit")
    public String processEditComponentForm(int componentId, String name, @RequestParam int deviceId, String description, int quantity) {
        Component component = componentRepository.findById(componentId).get();
        component.setName(name);
        component.setDescription(description);
        component.setQuantity(quantity);
        Device device = deviceRepository.findById(deviceId).get();
        component.setDevice(device);
        componentRepository.save(component);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteComponentForm(Model model) {
        model.addAttribute("components", componentRepository.findAll());
        return "components/delete";
    }

    @PostMapping("delete")
    public String processDeleteComponentForm(@RequestParam(required = false) int[] componentIds) {
        if (componentIds != null) {
            for (int id : componentIds) {
                componentRepository.deleteById(id);
            }
        }
        return "redirect:";
    }
}
