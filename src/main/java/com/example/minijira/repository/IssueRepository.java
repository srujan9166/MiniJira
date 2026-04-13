package com.example.minijira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minijira.model.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {

    Optional<Issue> findById(Long id);
    Optional<List<Issue>> findAllByProjectId(Long projectId);
}
