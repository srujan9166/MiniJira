package com.example.minijira.model;

import com.example.minijira.enums.LinkType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "issue_links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class IssueLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_issue_id")
    private Issue sourceIssue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="target_issue_id")
    private Issue targetIssue;
    @Enumerated(EnumType.STRING)
    private LinkType linkType;

}
