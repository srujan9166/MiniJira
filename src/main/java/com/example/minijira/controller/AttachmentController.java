package com.example.minijira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.minijira.service.AttachmentService;

import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping(value = "/issues/{id}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
        public ResponseEntity<?> uploadAttachment( @PathVariable Long id, @RequestParam("file") MultipartFile file){ 

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(attachmentService.uploadAttachment(id, file));
            
        }
}


