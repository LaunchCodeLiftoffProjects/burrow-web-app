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

@Controller
@RequestMapping("components")
public class ComponentController
{
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping
    public String displayAllDevices(Model model){
        model.addAttribute("components", componentRepository.findAll());
        return "components/index";
    }

    @GetMapping("add")
    public String displayAddComponentForm(Model model){
        model.addAttribute(new Component());
        model.addAttribute("devices", deviceRepository.findAll());
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
}
