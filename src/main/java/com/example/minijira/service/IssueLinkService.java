package com.example.minijira.service;

import org.springframework.stereotype.Service;

import com.example.minijira.dto.issueLinkDTO.IssueLinkCreate;
import com.example.minijira.exception.globalException.ResourceNotFoundException;
import com.example.minijira.model.Issue;
import com.example.minijira.model.IssueLink;
import com.example.minijira.repository.IssueLinkRepository;
import com.example.minijira.repository.IssueRepository;

@Service
public class IssueLinkService {
    private final IssueLinkRepository issueLinkRepository;
    private final IssueRepository issueRepository;
    public IssueLinkService(IssueLinkRepository issueLinkRepository, IssueRepository issueRepository) {
        this.issueLinkRepository = issueLinkRepository;
        this.issueRepository = issueRepository;
    }
    public  String linkIssues(IssueLinkCreate issueLinkCreate) {
        Issue sourceIssue = issueRepository.findById(issueLinkCreate.getSourceIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Source issue not found"));
        Issue targetIssue = issueRepository.findById(issueLinkCreate.getTargetIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Target issue not found"));

        IssueLink issueLink = new IssueLink();
        issueLink.setSourceIssue(sourceIssue);
        issueLink.setTargetIssue(targetIssue);
        issueLink.setLinkType(issueLinkCreate.getLinkType());
         issueLinkRepository.save(issueLink);
        return "Issues linked successfully between " + sourceIssue.getTitle() + " and " + targetIssue.getTitle() + " with link type " + issueLinkCreate.getLinkType();
    }



}
