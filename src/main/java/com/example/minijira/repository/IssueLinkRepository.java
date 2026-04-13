package com.example.minijira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minijira.model.IssueLink;

public interface IssueLinkRepository extends JpaRepository<IssueLink, Long> {

}
