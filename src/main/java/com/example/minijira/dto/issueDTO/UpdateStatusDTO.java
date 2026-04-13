package com.example.minijira.dto.issueDTO;

import com.example.minijira.enums.IssueStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusDTO {
    private IssueStatus issueStatus;

}
