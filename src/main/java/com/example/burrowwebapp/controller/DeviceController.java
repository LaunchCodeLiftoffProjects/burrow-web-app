package com.example.burrowwebapp.controller;
import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.Room;
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

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping
    public String displayAllDevices(Model model) {
        model.addAttribute("devices", deviceRepository.findAll());
        return "devices/index";
    }
    @GetMapping("add")
    public String displayAddDeviceForm(Model model) {
        model.addAttribute(new Device());
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("properties", propertyRepository.findAll());
        return "devices/add";
    }
    @PostMapping("add")
    public String processAddDeviceForm(@Valid @ModelAttribute Device newDevice,
                                       Errors errors, Model model, @RequestParam int roomId) {
        if (errors.hasErrors()) {
            model.addAttribute("rooms", roomRepository.findAll());
            return "devices/add";
        }
        Room room = roomRepository.findById(roomId).get();
        newDevice.setRoom(room);
        deviceRepository.save(newDevice);
        return "redirect:";
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
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("properties", propertyRepository.findAll());
        return "devices/edit";
    }
    @PostMapping("edit")
    public String processEditDeviceForm(int deviceId, String name, @RequestParam int roomId, String description) {
        Device device = deviceRepository.findById(deviceId).get();
        device.setName(name);
        Room room = roomRepository.findById(roomId).get();
        device.setRoom(room);
        device.setDescription(description);
        deviceRepository.save(device);
        return "redirect:";
    }
    @GetMapping("delete")
    public String displayDeleteDeviceForm(Model model) {
        model.addAttribute("title", "Delete Devices");
        model.addAttribute("devices", deviceRepository.findAll());
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("properties", propertyRepository.findAll());
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