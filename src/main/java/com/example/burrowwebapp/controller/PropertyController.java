package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.data.RoomRepository;
import com.example.burrowwebapp.models.Property;
import com.example.burrowwebapp.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private RoomRepository roomRepository;


    @GetMapping
    public String displayAllProperties(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
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
                                         Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "properties/add";
        }
        propertyRepository.save(newProperty);
        return "redirect:";
    }

    @GetMapping("view/{propertyId}")
    public String displayViewProperty(Model model, @PathVariable int propertyId) {

        Optional optProperty = propertyRepository.findById(propertyId);
        if (optProperty.isPresent()) {
            Property property = (Property) optProperty.get();
            model.addAttribute("property", property);
            return "properties/view";
        } else {
            return "redirect:../";
        }
    }

    @GetMapping("edit/{propertyId}")
    public String displayEditForm(Model model, @PathVariable int propertyId) {
        Property property = propertyRepository.findById(propertyId).get();
        model.addAttribute("property", property);
        model.addAttribute("propertyId", propertyId);
        return "properties/edit";
    }

    @PostMapping("edit")
    public String processEditForm(@Valid @ModelAttribute Property editProperty, Errors errors, Model model,
                                  int propertyId, String name, String location, String description) {

        if (errors.hasErrors()) {
            model.addAttribute("property", editProperty);
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

    @GetMapping("view")
    public String displayDeleteForm(Model model, @PathVariable int propertyId) {
        Property property = propertyRepository.findById(propertyId).get();
        model.addAttribute("property", property);
        return "redirect:";
    }

    @PostMapping("view")
    public String processDeleteForm(int propertyId) {
        propertyRepository.deleteById(propertyId);
        return "redirect:";
    }

    @GetMapping("addRoom/{propertyId}")
    public String displayAddRoom(Model model, @PathVariable int propertyId) {

        Optional optProperty = propertyRepository.findById(propertyId);
        if (optProperty.isPresent()) {
            Property property = (Property) optProperty.get();
            model.addAttribute(new Room("", property));
            model.addAttribute("property", property);
            return "properties/addRoom";
        } else {
            return "redirect:../";
        }
    }

    @PostMapping("addRoom/{propertyId}")
    public String processAddRoomForm(@Valid @ModelAttribute Room newRoom,
                                         Errors errors, Model model, @PathVariable int propertyId) {
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
                roomRepository.save(newRoom);

                return "redirect:../view/"+propertyId;
            }
        }else{
            return "redirect:../";
        }


    }

}
