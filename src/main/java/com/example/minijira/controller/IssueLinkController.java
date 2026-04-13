package com.example.minijira.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.issueLinkDTO.IssueLinkCreate;
import com.example.minijira.model.IssueLink;
import com.example.minijira.service.IssueLinkService;



@RestController
@RequestMapping("/api/issue-links")
public class IssueLinkController {

    private final IssueLinkService issueLinkService;
    public IssueLinkController(IssueLinkService issueLinkService) {
        this.issueLinkService = issueLinkService;
    }

    @PostMapping("/link")
    public ResponseEntity<?> linkIssues(@RequestBody IssueLinkCreate issueLinkCreate) {
       
        return ResponseEntity.status(HttpStatus.CREATED).body(issueLinkService.linkIssues(issueLinkCreate));
    }



}
