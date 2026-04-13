package com.example.minijira.dto.issueDTO;

import java.time.LocalDate;

import com.example.minijira.enums.IssuePriority;
import com.example.minijira.enums.IssueStatus;
import com.example.minijira.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestDTO {
    @NotBlank
    private String title;
    private String description;
    private IssueType issueType;
    private IssuePriority issuePriority;
    private IssueStatus issueStatus;
    @NotNull
    private Long assigneeId;
   
    @NotNull
    private Long projectId;
    private Integer storyPoints;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    

}
