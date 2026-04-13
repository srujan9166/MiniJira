package com.example.minijira.dto.projectDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String onwerName;
    private LocalDateTime createdAt;

}
