package com.example.minijira.dto.aiDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChatRequestDTO {
    private String model;
    private List<Message> messages;

}
