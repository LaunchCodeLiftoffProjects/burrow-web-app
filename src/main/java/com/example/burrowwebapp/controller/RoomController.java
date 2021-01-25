package com.example.burrowwebapp.controller;


import com.example.burrowwebapp.data.DeviceRepository;
import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.Property;
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
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private static final String userSessionKey = "user";

    @GetMapping
    public String displayAllRooms(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("rooms", roomRepository.findAllById(Collections.singleton(userId)));
        return "rooms/index";
    }

    @GetMapping(path = {"view/{roomId}", "view"})
    public String displayViewRoom(Model model, @PathVariable (required = false) Integer roomId) {
        if (roomId == null){
            model.addAttribute("rooms", roomRepository.findAll());
            return "rooms/index";
        } else {
            Optional<Room> result = roomRepository.findById(roomId);
            if (result.isEmpty()) {
                return "redirect:../";
            } else {
                Room room = result.get();
                model.addAttribute("room", room);
            }
        }
        return "rooms/view";
    }

    @GetMapping("edit/{roomId}")
    public String displayEditForm(Model model, @PathVariable int roomId) {
        Room room = roomRepository.findById(roomId).get();
        model.addAttribute("room", room);
        model.addAttribute("uneditedRoom", room);
        model.addAttribute("roomId", roomId);
        model.addAttribute("properties", propertyRepository.findAll());
        return "rooms/edit";
    }

    @PostMapping("edit")
    public String processEditForm(@Valid @ModelAttribute Room editRoom, Errors errors, int roomId, String name,
                                  @RequestParam int propertyId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("uneditedRoom", roomRepository.findById(roomId).get());
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

    @PostMapping("view")
    public String processDeleteForm(int roomId, int propertyId, RedirectAttributes redirectAttributes) {
        Optional optProperty = propertyRepository.findById(propertyId);
        redirectAttributes.addAttribute("id", optProperty.get());
        roomRepository.deleteById(roomId);
        return "redirect:/properties/view/{id}";
    }
}
