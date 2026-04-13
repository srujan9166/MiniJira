package com.example.minijira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.issueDTO.CreateRequestDTO;
import com.example.minijira.dto.issueDTO.CreateResponseDTO;
import com.example.minijira.dto.issueDTO.IssueRequestDTO;
import com.example.minijira.dto.issueDTO.UpdateStatusDTO;
import com.example.minijira.service.IssueService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;
    public IssueController(IssueService issueService){
        this.issueService= issueService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateResponseDTO> createIssue(@Valid @RequestBody CreateRequestDTO createRequestDTO){
        System.out.println("IssueController.createIssue called");
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(createRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIssueDetails(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueDetails(id));
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getIssuesByProjectId(@PathVariable Long projectId){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssuesByProjectId(projectId));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateIssue(@PathVariable Long id, @Valid @RequestBody IssueRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssue(id, request));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable Long id){
        issueService.deleteIssue(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateIssueStatus(@PathVariable Long id, @RequestBody UpdateStatusDTO updateStatusDTO){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssueStatus(id, updateStatusDTO));
    }

    @PatchMapping("/{id}/assignee")
    public ResponseEntity<?> updateIssueAssignee(@PathVariable Long id, @RequestBody Long assigneeId){
        return ResponseEntity.status(HttpStatus.OK).body(issueService.updateIssueAssignee(id, assigneeId));
    }


}
