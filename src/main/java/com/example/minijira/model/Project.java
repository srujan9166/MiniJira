package com.example.minijira.model;

import java.time.LocalDateTime;
import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name ="projects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name ="name")
    private String name;
    @Column(name ="project_key" ,unique = true)
    private String projectKey;
    @Column(name ="description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id" , nullable = false)
    private User user;
    @Column(name ="created_at")
    private LocalDateTime createdAt;
    @Column(name = "is_archived" )
    private boolean isArchived;
    @OneToMany(mappedBy = "project")
    private Set<ProjectMember> projectMembers;
    @OneToMany(mappedBy = "project")
    private Set<Label> labels;
    @OneToMany(mappedBy = "project")
    private Set<Issue> issues;





}
