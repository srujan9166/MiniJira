package com.example.minijira.dto.sprintDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.minijira.enums.SprintStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateSprintRequestDTO {
    @NotBlank
    private String name;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private SprintStatus sprintStatus;
    @NotNull
    private Long projectId;



}
