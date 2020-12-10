package com.example.burrowwebapp.controller;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;
@Controller
@RequestMapping("devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;
    @GetMapping
    public String displayAllDevices(Model model) {
        model.addAttribute("devices", deviceRepository.findAll());
        return "devices/index";
    }
    @GetMapping("add")
    public String displayAddDeviceForm(Model model) {
        model.addAttribute(new Device());
        model.addAttribute("devices", deviceRepository.findAll());
        return "devices/add";
    }
    @PostMapping("add")
    public String processAddDeviceForm(@Valid @ModelAttribute Device newDevice,
                                       Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "devices/add";
        }
        deviceRepository.save(newDevice);
        return "redirect:";
    }
    @GetMapping("view/{deviceID}")
    public String displayViewDevice(Model model, @PathVariable int deviceID) {
        Optional optDevice = deviceRepository.findById(deviceID);
        if (optDevice.isPresent()) {
            Device device = (Device) optDevice.get();
            model.addAttribute("device", device);
            return "devices/view";
        } else {
            return "redirect:../";
        }
    }
    @GetMapping("edit/{deviceID}")
    public String displayEditDeviceForm(Model model, @PathVariable int deviceID) {
        Device device = deviceRepository.findById(deviceID).get();
        model.addAttribute("device", device);
        return "devices/edit";
    }
    @PostMapping("edit")
    public String processEditDeviceForm(int deviceID, String name, String room, String description) {
        Device device = deviceRepository.findById(deviceID).get();
        device.setName(name);
        device.setRoom(room);
        device.setDescription(description);
        deviceRepository.save(device);
        return "redirect:";
    }
    @GetMapping("delete")
    public String displayDeleteDeviceForm(Model model) {
        model.addAttribute("title", "Delete Devices");
        model.addAttribute("devices", deviceRepository.findAll());
        return "devices/delete";
    }
    @PostMapping("delete")
    public String processDeleteDeviceForm(@RequestParam(required = false) int[] deviceIds) {
        if (deviceIds != null) {
            for (int id : deviceIds) {
                deviceRepository.deleteById(id);
            }
        }
        return "redirect:";
    }
}