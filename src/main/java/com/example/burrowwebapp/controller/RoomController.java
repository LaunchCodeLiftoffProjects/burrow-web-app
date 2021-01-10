package com.example.burrowwebapp.controller;


import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.models.AbstractEntity;
import com.example.burrowwebapp.models.Property;
import com.example.burrowwebapp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("rooms")
public class RoomController extends AbstractEntity {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    @GetMapping
    public String displayAllRooms(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("devices", deviceRepository.findAll());
        return "rooms/index";
    }

    @GetMapping("view/{roomId}")
    public String displayViewRoom(Model model, @PathVariable int roomId) {

        Optional optRoom = roomRepository.findById(roomId);
        if (optRoom.isPresent()) {
            Room room = (Room) optRoom.get();
            model.addAttribute("room", room);
            return "rooms/view";
        } else {
            return "redirect:../";
        }
    }

    @GetMapping("edit/{roomId}")
    public String displayEditForm(Model model, @PathVariable int roomId) {
        Room room = roomRepository.findById(roomId).get();
        model.addAttribute("room", room);
        model.addAttribute("roomId", roomId);
        model.addAttribute("properties", propertyRepository.findAll());
        return "rooms/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int roomId, String name, @RequestParam int propertyId, Model model,
                                  @Valid @ModelAttribute Room editRoom, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("room", editRoom);
            model.addAttribute("roomId", roomId);
            model.addAttribute("properties", propertyRepository.findAll());
            return "rooms/edit";
        }
        Room room = roomRepository.findById(roomId).get();
        Property property = propertyRepository.findById(propertyId).get();
        room.setName(name);
        room.setProperty(property);
        roomRepository.save(room);
        return "redirect:view/" + roomId;
    }

    @GetMapping("view")
    public String displayDeleteForm(Model model, @PathVariable int roomId) {
        Room room = roomRepository.findById(roomId).get();
        model.addAttribute("room", room);
        return "redirect:";
    }

    @PostMapping("view")
    public String processDeleteForm(int roomId, int propertyId, RedirectAttributes redirectAttributes) {
        Optional optProperty = propertyRepository.findById(propertyId);
        redirectAttributes.addAttribute("id", optProperty.get());
        roomRepository.deleteById(roomId);
        return "redirect:/properties/view/{id}";
    }

    @Override
    public Property getProperty() {
        return null;
    }
}
