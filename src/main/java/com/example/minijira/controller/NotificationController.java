package com.example.minijira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.notificationDTO.NotificationSendDTO;
import com.example.minijira.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send/{userId}")
    public ResponseEntity<?> sendNotification(@PathVariable Long userId, @RequestBody NotificationSendDTO notificationSendDTO) {
        
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.sendNotification(userId, notificationSendDTO));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.markAsRead(notificationId));
    }

    @PatchMapping("/{notificationId}/allRead")
    public ResponseEntity<?> markAllAsRead(@PathVariable Long notificationId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.markAllAsRead(notificationId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllNotifications());
    }
}
