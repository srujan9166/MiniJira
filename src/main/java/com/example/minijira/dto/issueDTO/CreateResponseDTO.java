package com.example.minijira.dto.issueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateResponseDTO {
    private Long id;
    private String issueTitle;
    private String projectName;
    private String assigneUser;
    private String reportUser;


}
