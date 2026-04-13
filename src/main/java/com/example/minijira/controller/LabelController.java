package com.example.minijira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.labelDTO.LabelCreateDTO;
import com.example.minijira.service.LabelService;

import aj.org.objectweb.asm.Label;
import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/labels")
public class LabelController {

    private final LabelService labelService;
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLabel(@Valid  @RequestBody LabelCreateDTO labelCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labelService.createLabel(labelCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLabelById(@PathVariable Long id) {
        return ResponseEntity.ok(labelService.getLabelById(id));
    }

    @DeleteMapping("/{id}")   
    public ResponseEntity<?> deleteLabelById(@PathVariable Long id) {
       labelService.deleteLabelById(id);
        return ResponseEntity.ok("Label with ID " + id + " deleted successfully"); 
    
    }


}
