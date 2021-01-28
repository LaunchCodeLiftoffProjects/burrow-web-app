package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.data.UserRepository;
import com.example.burrowwebapp.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";
    
    @GetMapping
    public String displayAllProperties(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        model.addAttribute("user", user);
        model.addAttribute("properties", propertyRepository.findAllById(Collections.singleton(userId)));
        return "properties/index";
    }

    @GetMapping("add")
    public String displayAddPropertyForm(Model model) {
        model.addAttribute(new Property());
        model.addAttribute("properties", propertyRepository.findAll());
        return "properties/add";
    }

    @PostMapping("add")
    public String processAddPropertyForm(@Valid @ModelAttribute Property newProperty,
                                         Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "properties/add";
        }
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        newProperty.setUser(user);
        propertyRepository.save(newProperty);
        return "redirect:";
    }

    @GetMapping(path = {"view/{propertyId}", "view"})
    public String displayViewProperty(Model model, @PathVariable(required = false) Integer propertyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        if (propertyId == null){
            model.addAttribute("user", user);
            model.addAttribute("properties", propertyRepository.findAllById(Collections.singleton(userId)));
            return "properties/index";
        } else {
            Optional<Property> result = propertyRepository.findById(propertyId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Property property = result.get();
                if (user.getId() != property.getUser().getId()) {
                    return "redirect:../";
                }
                model.addAttribute("property", property);
            }
        }
        return "properties/view";
    }

    @GetMapping("edit/{propertyId}")
    public String displayEditForm(Model model, @PathVariable int propertyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        Optional optProperty = propertyRepository.findById(propertyId);
        if (optProperty.isPresent()) {
            Property property = propertyRepository.findById(propertyId).get();
            if (user.getId() != property.getUser().getId()) {
                return "redirect:../";
            }
            model.addAttribute("property", property);
            model.addAttribute("uneditedProperty", property);
            model.addAttribute("propertyId", propertyId);
            return "properties/edit";
        } else {
            return "redirect:../";
        }
    }

    @PostMapping("edit")
    public String processEditForm(@Valid @ModelAttribute Property editProperty, Errors errors, Model model,
                                  int propertyId, String name, String location, String description) {

        if (errors.hasErrors()) {
            model.addAttribute("property", editProperty);
            model.addAttribute("uneditedProperty", propertyRepository.findById(propertyId).get());
            model.addAttribute("propertyId", propertyId);
            return "properties/edit";
        }
        Property property = propertyRepository.findById(propertyId).get();
        property.setName(name);
        property.setLocation(location);
        property.setDescription(description);
        propertyRepository.save(property);
        return "redirect:view/" + propertyId;
    }

    @PostMapping("view")
    public String processDeleteForm(int propertyId) {
        propertyRepository.deleteById(propertyId);
        return "redirect:";
    }

    @GetMapping("addRoom/{propertyId}")
    public String displayAddRoom(Model model, @PathVariable int propertyId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User user = userRepository.findById(userId).get();
        Optional optProperty = propertyRepository.findById(propertyId);
        if (optProperty.isPresent()) {
            Property property = (Property) optProperty.get();
            model.addAttribute(new Room("", property));
            model.addAttribute("property", property);
            if (user.getId() != property.getUser().getId()) {
                return "redirect:../";
            }
            return "properties/addRoom";
        } else {
            return "redirect:../";
        }
    }

    @PostMapping("addRoom/{propertyId}")
    public String processAddRoomForm(@Valid @ModelAttribute Room newRoom,
                                         Errors errors, Model model, @PathVariable int propertyId, HttpSession session) {
        Optional optProperty = propertyRepository.findById(propertyId);
        if (optProperty.isPresent())
        {
            if (errors.hasErrors())
            {
                Property property = (Property) optProperty.get();
                model.addAttribute(new Room("", property));
                model.addAttribute("property", property);
                return "properties/addRoom";
            }else{
                Integer userId = (Integer) session.getAttribute(userSessionKey);
                User user = userRepository.findById(userId).get();
                Property property = (Property) optProperty.get();
                newRoom.setUser(user);
                newRoom.setProperty(property);
                roomRepository.save(newRoom);

                return "redirect:../view/"+propertyId;
            }
        }else{
            return "redirect:../";
        }


    }
}
