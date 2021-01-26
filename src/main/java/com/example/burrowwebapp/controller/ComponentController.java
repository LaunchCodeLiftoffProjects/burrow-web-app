package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.ComponentRepository;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.NotificationRepository;
import com.example.burrowwebapp.models.Component;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
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
    private NotificationRepository notificationRepository;

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

    @GetMapping(path = {"add/{deviceId}", "add"})
    public String displayAddComponent(Model model, @PathVariable (required = false) Integer deviceId){
        if (deviceId == null) {
            model.addAttribute("components", componentRepository.findAll());
            return "redirect:../devices/";
        } else {
            Optional optDevice = deviceRepository.findById(deviceId);
            if (optDevice.isPresent()) {
                Device device = (Device) optDevice.get();
                model.addAttribute(new Component());
                model.addAttribute("device", device);
                model.addAttribute("names", nameList);
                return "components/add";
            } else {
                return "redirect:../";
            }
        }
    }

    @PostMapping("add/{deviceId}")
    public String processAddComponentForm(@Valid @ModelAttribute Component newComponent,
                                          Errors errors, Model model, @PathVariable int deviceId,
                                          RedirectAttributes redirectAttributes){
        if (errors.hasErrors()) {
            model.addAttribute("device", deviceRepository.findById(deviceId).get());
            model.addAttribute("names", nameList);
            return "components/add";
        }
        Optional optDevice = deviceRepository.findById(deviceId);
        Device device = deviceRepository.findById(deviceId).get();
        redirectAttributes.addAttribute("id", optDevice.get());
        newComponent.setDevice(device);
        Notification notification = new Notification("It has been at least " + newComponent.getDaysBetweenReplacements() + " days since you replaced the " + newComponent.getName() + " in the " + newComponent.getDevice().getName(), newComponent.getInstallDate(), newComponent.getDaysBetweenReplacements());
        newComponent.setNotification(notification);
        notification.setComponent(newComponent);
        componentRepository.save(newComponent);
        notificationRepository.save(notification);
        return "redirect:/devices/view/{id}";
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
        model.addAttribute("uneditedComponent", component);
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("names", nameList);
        return "components/edit";
    }

    @PostMapping("edit")
    public String processEditComponentForm(@Valid @ModelAttribute Component editComponent, Errors errors, int componentId,
                                           String name, @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate installDate,
                                           @RequestParam int deviceId, String description, Integer quantity, Model model) {

        if(errors.hasErrors()){
            model.addAttribute("component", editComponent);
            model.addAttribute("uneditedComponent", componentRepository.findById(componentId).get());
            model.addAttribute("componentId", componentId);
            model.addAttribute("devices", deviceRepository.findAll());
            model.addAttribute("names", nameList);
            return "components/edit";
        }

        Component component = componentRepository.findById(componentId).get();
        component.setName(name);
        component.setDescription(description);
        component.setQuantity(quantity);
        component.setInstallDate(installDate);
        Device device = deviceRepository.findById(deviceId).get();
        component.setDevice(device);
        componentRepository.save(component);
        return "redirect:view/" + componentId;
    }

    @PostMapping("view")
    public String processDeleteComponentForm(int componentId, int deviceId, RedirectAttributes redirectAttributes) {
        Optional optDevice = deviceRepository.findById(deviceId);
        redirectAttributes.addAttribute("id", optDevice.get());
        componentRepository.deleteById(componentId);
        return "redirect:/devices/view/{id}";
    }
}
