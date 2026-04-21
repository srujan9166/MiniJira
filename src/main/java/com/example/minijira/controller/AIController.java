package com.example.minijira.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @PostMapping("/ask")
    public String askAI(@RequestParam String prompt){
        return "This is a response from AI for the prompt: " + prompt;
    }

}
