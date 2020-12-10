package com.example.burrowwebapp.controller;

import com.example.burrowwebapp.data.PropertyRepository;
import com.example.burrowwebapp.models.Property;
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
        return "properties/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int propertyId, String name, String location, String description) {
        Property property = propertyRepository.findById(propertyId).get();
        property.setName(name);
        property.setLocation(location);
        property.setDescription(description);
        propertyRepository.save(property);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model) {
        model.addAttribute("title", "Delete Properties");
        model.addAttribute("properties", propertyRepository.findAll());
        return "properties/delete";
    }

    @PostMapping("delete")
    public String processDeleteForm(@RequestParam(required = false) int[] propertyIds) {
        if (propertyIds != null) {
            for (int id : propertyIds) {
                propertyRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

}
