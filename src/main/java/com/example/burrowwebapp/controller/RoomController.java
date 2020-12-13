package com.example.burrowwebapp.controller;


import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;


    @GetMapping
    public String displayAllRooms(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms/index";
    }

    @GetMapping("add")
    public String displayAddRoomForm(Model model) {
        model.addAttribute(new Room());
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms/add";
    }

    @PostMapping("add")
    public String processAddRoomForm(@Valid @ModelAttribute Room newRoom,
                                     Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "rooms/add";
        }
        roomRepository.save(newRoom);
        return "redirect:";
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
}