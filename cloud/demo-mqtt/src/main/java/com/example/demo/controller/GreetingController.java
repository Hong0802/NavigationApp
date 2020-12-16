package com.example.demo.controller;

import com.example.demo.service.MessagingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.demo.model.Greeting;

@Controller
public class GreetingController {

    MessagingService messagingService;

    @Autowired
    public GreetingController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @GetMapping("/location")
    public String locationSubmit(@ModelAttribute Greeting greeting, Model model) throws JsonProcessingException {

        model.addAttribute("lat", messagingService.getLatitude());
        model.addAttribute("lng", messagingService.getLongitude());
        return "location";
    }

    @GetMapping("/marker")
    public String markerSubmit(@ModelAttribute Greeting greeting, Model model) throws JsonProcessingException {

        model.addAttribute("lat", messagingService.getLatitude());
        model.addAttribute("lng", messagingService.getLongitude());
        return "marker";
    }



}