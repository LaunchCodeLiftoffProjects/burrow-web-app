//package com.example.burrowwebapp.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("properties")
//public class PropertyController {
//
////    @Autowired
////    private PropertyRepository propertyRepository;
//
//
//    @GetMapping
//    public String displayAllProperties(Model model) {
//        model.addAttribute("properties", propertyRepository.findAll());
//        return "properties/index";
//    }
//
//    @GetMapping("add")
//    public String displayAddPropertyForm(Model model) {
//        model.addAttribute(new Property());
//        model.addAttribute("properties", propertyRepository.findAll());
//        return "properties/add";
//    }
//
//    @PostMapping("add")
//    public String processAddPropertyForm(@Valid @ModelAttribute Property newProperty,
//                                         Errors errors, Model model) {
//        if (errors.hasErrors()) {
//            return "properties/add";
//        }
//        propertyRepository.save(newProperty);
//        return "redirect:";
//    }
//
//    @GetMapping("view/{propertyId}")
//    public String displayViewProperty(Model model, @PathVariable int propertyId) {
//
//        Optional optProperty = propertyRepository.findById(propertyId);
//        if (optProperty.isPresent()) {
//            Property property = (Property) optProperty.get();
//            model.addAttribute("property", property);
//            return "properties/view";
//        } else {
//            return "redirect:../";
//        }
//    }
//
//}
