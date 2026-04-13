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
public class ProjectDetailsDTO {

    private Long id;
    private String name;
    private String projectKey;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private boolean isArchive;

}
