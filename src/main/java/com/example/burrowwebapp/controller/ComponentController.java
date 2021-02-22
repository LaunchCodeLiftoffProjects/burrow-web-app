package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.ComponentRepository;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.NotificationRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.Component;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.Notification;
import com.example.burrowwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("components")
public class ComponentController
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    private static final String userSessionKey = "user";

    private ArrayList<String> nameList = new ArrayList<String>() {
        {
            add("Light Bulb");
            add("Battery");
            add("Filter");
        }
    };

    @GetMapping
    public String displayAllComponents(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("components", componentRepository.findAllById(Collections.singleton(userId)));
        return "components/index";
    }

    @GetMapping(path = {"add/{deviceId}", "add"})
    public String displayAddComponentForm(Model model, @PathVariable(required = false) Integer deviceId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (deviceId == null) {
            model.addAttribute("user", user);
            model.addAttribute("components", componentRepository.findAllById(Collections.singleton(userId)));
            return "components/index";
        } else {
            Optional<Device> result = deviceRepository.findById(deviceId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Device device = result.get();
                if (user.getId() != device.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute(new Component());
                model.addAttribute("device", device);
                model.addAttribute("names", nameList);
            }
        }
        return "components/add";
    }

    @PostMapping("add/{deviceId}")
    public String processAddComponentForm(@Valid @ModelAttribute Component newComponent,
                                          Errors errors, Model model, @PathVariable int deviceId,
                                          HttpSession session, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()) {
            model.addAttribute("device", deviceRepository.findById(deviceId).get());
            model.addAttribute("names", nameList);
            return "components/add";
        }
        Optional optDevice = deviceRepository.findById(deviceId);
        Device device = deviceRepository.findById(deviceId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optDevice.get());
        newComponent.setUser(user);
        newComponent.setDevice(device);
        Notification notification = new Notification("", newComponent.getInstallDate(), newComponent.getDaysBetweenReplacements());
        newComponent.setNotification(notification);
        notification.setComponent(newComponent);
        notification.setUser(user);
        notification.setMessage();
        componentRepository.save(newComponent);
        notificationRepository.save(notification);
        return "redirect:/devices/view/{id}";
    }

    @GetMapping(path = {"view/{componentId}", "view"})
    public String displayViewComponent(Model model, @PathVariable(required = false) Integer componentId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (componentId == null) {
            model.addAttribute("user", user);
            model.addAttribute("components", componentRepository.findAllById(Collections.singleton(userId)));
            return "components/index";
        } else {
            Optional<Component> result = componentRepository.findById(componentId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Component component = result.get();
                if (user.getId() != component.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("component", component);
            }
        }
        return "components/view";
    }

    @GetMapping(path = {"edit/{componentId}", "edit"})
    public String displayEditComponentForm(Model model, @PathVariable(required = false) Integer componentId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (componentId == null) {
            model.addAttribute("user", user);
            model.addAttribute("components", componentRepository.findAllById(Collections.singleton(userId)));
            return "components/index";
        } else {
            Optional<Component> result = componentRepository.findById(componentId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Component component = result.get();
                if (user.getId() != component.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("component", component);
                model.addAttribute("uneditedComponent", component);
                model.addAttribute("devices", deviceRepository.findAll());
                model.addAttribute("names", nameList);
            }
        }
        return "components/edit";
    }

    @PostMapping("edit")
    public String processEditComponentForm(@Valid @ModelAttribute Component editComponent, Errors errors, int componentId,
                                           String name, @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate installDate,
                                           @RequestParam int deviceId, String description, Integer quantity, Model model, Long daysBetweenReplacements) {

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
        component.setDaysBetweenReplacements(daysBetweenReplacements);
        Notification notification = component.getNotification();
        notification.setReplacedDate(installDate);
        notification.setDaysBetweenReplacements(daysBetweenReplacements);
        notification.setMessage();
        notificationRepository.save(notification);
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
