package com.example.burrowwebapp.controller;


import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.models.Device;
import com.example.burrowwebapp.models.Property;
import com.example.burrowwebapp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

    @GetMapping("edit/{roomId}")
    public String displayEditForm(Model model, @PathVariable int roomId) {
        Room room = roomRepository.findById(roomId).get();
        model.addAttribute("room", room);
        return "rooms/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int roomId, String name) {
        Room room = roomRepository.findById(roomId).get();
        room.setName(name);
        roomRepository.save(room);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model) {
        model.addAttribute("title", "Delete Rooms");
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms/delete";
    }

    @PostMapping("delete")
    public String processDeleteForm(@RequestParam(required = false) int[] roomIds) {
        if (roomIds != null) {
            for (int id : roomIds) {
                roomRepository.deleteById(id);
            }
        }
        return "redirect:";
    }
}