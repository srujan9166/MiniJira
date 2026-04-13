package com.example.minijira.dto.issueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String issueType;
    private String issuePriority;
    private String issueStatus;
    private String assigneeName;
    private String reporterName;
    private String projectName;
    private Integer storyPoints;
    private String dueDate;
    private String createdAt;

}
