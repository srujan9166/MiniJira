package com.example.minijira.dto.notificationDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class NotificationResponseDTO {

    private Long id;
    private String username;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;


}
