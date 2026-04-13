package com.example.minijira.dto.issueLinkDTO;

import com.example.minijira.enums.LinkType;

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

public class IssueLinkCreate {

    private Long sourceIssueId;
    private Long targetIssueId;
    private LinkType linkType;

}
