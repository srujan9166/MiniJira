package com.example.minijira.dto.issueDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.minijira.enums.IssuePriority;
import com.example.minijira.enums.IssueStatus;
import com.example.minijira.enums.IssueType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequestDTO {

    private String title;
    private String description;
    private IssueType issueType;
    private IssuePriority issuePriority;
    private IssueStatus issueStatus;
  
    private String assigneeUsername;
     private Long projectId;
     private Integer storyPoints;
    private LocalDate dueDate;
   
    
     

}
