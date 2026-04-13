package com.example.minijira.dto.sprintDTO;

import java.time.LocalDate;

import com.example.minijira.enums.SprintStatus;
import com.example.minijira.model.Sprint;

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

public class CreateSprintDTO {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private SprintStatus status;
    

   

}
