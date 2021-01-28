package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.Room;
import com.example.burrowwebapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;
@Controller
@RequestMapping("devices")
public class DeviceController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllDevices(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("devices", deviceRepository.findAllById(Collections.singleton(userId)));
        return "devices/index";
    }

    @GetMapping(path={"add/{roomId}", "add"})
    public String displayAddDeviceForm(Model model, @PathVariable(required = false) Integer roomId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (roomId == null) {
            model.addAttribute("devices", deviceRepository.findAll());
            return "redirect:../rooms/";
        } else {
            Optional optRoom = roomRepository.findById(roomId);
            if (optRoom.isPresent()) {
                Room room = (Room) optRoom.get();
                model.addAttribute(new Device());
                model.addAttribute("room", room);
                if (user.getId() != room.getUser().getId()) {
                    return "redirect:../";
                }
                return "devices/add";
            } else {
                return "redirect:../";
            }
        }
    }

    @PostMapping("add/{roomId}")
    public String processAddDeviceForm(@Valid @ModelAttribute Device newDevice,
                                       Errors errors, Model model, @PathVariable int roomId,
                                       HttpSession session, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("room", roomRepository.findById(roomId).get());
            return "devices/add";
        }
        Optional optRoom = roomRepository.findById(roomId);
        Room room = roomRepository.findById(roomId).get();
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        redirectAttributes.addAttribute("id", optRoom.get());
        newDevice.setUser(user);
        newDevice.setRoom(room);
        deviceRepository.save(newDevice);
        return "redirect:/rooms/view/{id}";
    }

    @GetMapping(path = {"view/{deviceID}", "view"})
    public String displayViewDevice(Model model, @PathVariable(required = false) Integer deviceID, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (deviceID == null) {
            model.addAttribute("user", user);
            model.addAttribute("devices", deviceRepository.findAllById(Collections.singleton(userId)));
            return "devices/index";
        } else {
            Optional<Device> result = deviceRepository.findById(deviceID);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Device device = result.get();
                if (user.getId() != device.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("device", device);
            }
        }
        return "devices/view";
    }

    @GetMapping("edit/{deviceId}")
    public String displayEditDeviceForm(Model model, @PathVariable int deviceId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        Optional optDevice = deviceRepository.findById(deviceId);
        if (optDevice.isPresent()) {
            Device device = deviceRepository.findById(deviceId).get();
            if (user.getId() != device.getUser().getId()) {
                return "redirect:../";
            }
            model.addAttribute("device", device);
            model.addAttribute("uneditedDevice", device);
            model.addAttribute("rooms", roomRepository.findAll());
            model.addAttribute("properties", propertyRepository.findAll());
            return "devices/edit";
        } else {
            return "redirect:../";
        }
    }

    @PostMapping("edit")
    public String processEditDeviceForm(@Valid @ModelAttribute Device editDevice, Errors errors, int deviceId,
                                        String name, @RequestParam int roomId, String description, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("device", editDevice);
            model.addAttribute("uneditedDevice", deviceRepository.findById(deviceId).get());
            model.addAttribute("deviceId", deviceId);
            model.addAttribute("rooms", roomRepository.findAll());
            model.addAttribute("properties", propertyRepository.findAll());
            return "devices/edit";
        }

        Device device = deviceRepository.findById(deviceId).get();
        device.setName(name);
        Room room = roomRepository.findById(roomId).get();
        device.setRoom(room);
        device.setDescription(description);
        deviceRepository.save(device);
        return "redirect:view/" + deviceId;
    }

    @PostMapping("view")
    public String processDeleteDeviceForm(int deviceId, int roomId, RedirectAttributes redirectAttributes) {
        Optional optRoom = roomRepository.findById(roomId);
        redirectAttributes.addAttribute("id", optRoom.get());
        deviceRepository.deleteById(deviceId);
        return "redirect:/rooms/view/{id}";
    }
}