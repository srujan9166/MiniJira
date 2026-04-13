package com.example.minijira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minijira.model.Comment;
import com.example.minijira.model.Issue;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByIssue(Issue issue);
    

}
