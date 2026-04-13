package com.example.minijira.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.sprintDTO.AddIssuesToSprintRequest;
import com.example.minijira.dto.sprintDTO.CreateSprintDTO;
import com.example.minijira.dto.sprintDTO.UpdateSprintRequestDTO;
import com.example.minijira.service.SprintService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    private final SprintService sprintService;
    public SprintController(SprintService sprintService){
        this.sprintService = sprintService;
    }
    @GetMapping
    public ResponseEntity<?> getAllSprints(){ 
        return ResponseEntity.ok(sprintService.getAllSprints());
    }
    @GetMapping("/projects/{id}/sprints")
    public ResponseEntity<?> getSprintsByProjectId( @PathVariable Long id){ 
        return ResponseEntity.ok(sprintService.getSprintsByProjectId(id));
    }

    @PostMapping("/projects/{id}/sprints")
    public ResponseEntity<?> createSprint(@PathVariable Long id , @RequestBody CreateSprintDTO createSprintDTO){ 
        
        return ResponseEntity.status(HttpStatus.CREATED).body(sprintService.createSprint(id, createSprintDTO));
    }

    @PutMapping("/sprints/{id}")
    public ResponseEntity<?> updateSprint(@Valid @PathVariable Long id , @RequestBody UpdateSprintRequestDTO updateSprintRequestDTO){ 
        
        return ResponseEntity.status(HttpStatus.OK).body(sprintService.updateSprint(id, updateSprintRequestDTO));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<?> startSprint(@PathVariable Long id){ 
        return ResponseEntity.ok(sprintService.startSprint(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeSprint(@PathVariable Long id){ 
        return ResponseEntity.ok(sprintService.completeSprint(id));
    }

    @PostMapping("/{id}/issues")
public ResponseEntity<String> addIssuesToSprint(
        @PathVariable Long id,
        @RequestBody AddIssuesToSprintRequest request) {

    sprintService.addIssuesToSprint(id, request.getIssueIds());
    return ResponseEntity.ok("Issues added to sprint successfully");
}

    @DeleteMapping("/{id}/issues/{issueId}")
public ResponseEntity<String> removeIssueFromSprint(
        @PathVariable Long id,
        @PathVariable Long issueId) {

    sprintService.removeIssueFromSprint(id, issueId);
    return ResponseEntity.ok("Issue removed from sprint successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSprint(@PathVariable Long id){ 
        sprintService.deleteSprint(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
