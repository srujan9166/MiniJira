package com.example.minijira.dto.projectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberDTO {
    private Long projectId;
    private Long userId;
    private String ProjectName;
    private String userName;
    private String role;

}
