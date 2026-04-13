package com.example.minijira.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.minijira.enums.IssuePriority;
import com.example.minijira.enums.IssueStatus;
import com.example.minijira.enums.IssueType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "issues")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private IssuePriority issuePriority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private IssueStatus issueStatus;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "project_id")
   
    private Project project;

    // @ManyToOne
    // @JoinColumn(name = "sprint_id")
    // private Sprint sprint;
     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    private Integer storyPoints;

    private LocalDate dueDate;

    @CreationTimestamp
    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
    private List<Comment> comments;

}