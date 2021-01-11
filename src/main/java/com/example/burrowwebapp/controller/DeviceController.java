package com.example.burrowwebapp.controller;
import com.example.burrowwebapp.data.ComponentRepository;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;
@Controller
@RequestMapping("devices")
public class DeviceController extends AbstractEntity {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping
    public String displayAllDevices(Model model) {
        model.addAttribute("devices", deviceRepository.findAll());
        return "devices/index";
    }
    @GetMapping(path={"add/{roomId}", "add"})
    public String displayAddDeviceForm(Model model, @PathVariable(required = false) Integer roomId) {
       if (roomId == null) {
           model.addAttribute("devices", deviceRepository.findAll());
           return "redirect:../rooms/";
       } else {
           Optional optRoom = roomRepository.findById(roomId);
           if (optRoom.isPresent()) {
               Room room = (Room) optRoom.get();
               model.addAttribute(new Device());
               model.addAttribute("room", room);
               return "devices/add";
           } else {
               return "redirect:../";
           }
       }
    }

    @PostMapping("add/{roomId}")
    public String processAddDeviceForm(@Valid @ModelAttribute Device newDevice,
                                       Errors errors, Model model, @PathVariable int roomId) {
        if (errors.hasErrors()) {
            model.addAttribute("room", roomRepository.findById(roomId).get());
            return "devices/add";
        }
        Room room = roomRepository.findById(roomId).get();
        newDevice.setRoom(room);
        int deviceId = newDevice.getId();
        deviceRepository.save(newDevice);
        return "redirect:../";
    }

    @GetMapping(path = {"view/{deviceID}", "view"})
    public String displayViewDevice(Model model, @PathVariable (required = false) Integer deviceID) {
        if (deviceID == null) {
            model.addAttribute("devices", deviceRepository.findAll());
            return "devices/index";
        } else {
            Optional<Device> result = deviceRepository.findById(deviceID);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Device device = result.get();
                model.addAttribute("device", device);
            }
        }
        return "devices/view";
    }

    @GetMapping("edit/{deviceId}")
    public String displayEditDeviceForm(Model model, @PathVariable int deviceId) {
        Device device = deviceRepository.findById(deviceId).get();
        model.addAttribute("device", device);
        model.addAttribute("uneditedDevice", device);
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("properties", propertyRepository.findAll());
        return "devices/edit";
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

    @GetMapping("view")
    public String displayDeleteDeviceForm(Model model, @PathVariable int deviceId) {
        Device device = deviceRepository.findById(deviceId).get();
        model.addAttribute("device", device);
        return "redirect:";
    }
    @PostMapping("view")
    public String processDeleteDeviceForm(int deviceId, int roomId, RedirectAttributes redirectAttributes) {
        Optional optRoom = roomRepository.findById(roomId);
        redirectAttributes.addAttribute("id", optRoom.get());
        deviceRepository.deleteById(deviceId);
        return "redirect:/rooms/view/{id}";
    }

    @Override
    public Property getProperty() {
        return null;
    }
}