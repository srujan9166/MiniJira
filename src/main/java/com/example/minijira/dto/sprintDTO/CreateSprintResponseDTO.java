package com.example.minijira.dto.sprintDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSprintResponseDTO {

    private Long id;
    private String name;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;


}
