package com.example.minijira.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.minijira.dto.issueDTO.CreateRequestDTO;
import com.example.minijira.dto.issueDTO.CreateResponseDTO;
import com.example.minijira.dto.issueDTO.IssueRequestDTO;
import com.example.minijira.dto.issueDTO.IssueResponseDTO;
import com.example.minijira.dto.issueDTO.UpdateStatusDTO;
import com.example.minijira.dto.notificationDTO.NotificationSendDTO;
import com.example.minijira.model.Issue;
import com.example.minijira.model.Project;
import com.example.minijira.model.User;
import com.example.minijira.repository.IssueRepository;
import com.example.minijira.repository.ProjectRepository;
import com.example.minijira.repository.UserRepository;

@Service
public class IssueService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final AuthServiceImpl authServiceImpl;
    private final IssueRepository issueRepository;
    private final NotificationService notificationService;
    private final EmailService emailService;


    public IssueService(UserRepository userRepository , ProjectRepository projectRepository , AuthServiceImpl authServiceImpl, IssueRepository issueRepository, NotificationService notificationService, EmailService emailService){
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.authServiceImpl = authServiceImpl;
        this.issueRepository= issueRepository;
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    public CreateResponseDTO createIssue(CreateRequestDTO createRequestDTO){
        User assignUser = userRepository.findById(createRequestDTO.getAssigneeId())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found!"));
        Project project = projectRepository.findById(createRequestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project Not Found!"));
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String name =  authentication.getName();
       System.out.println(name);
        User reportUser = userRepository.findByUsername(name)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));

       

        // System.out.println("AssigneeId: " + createRequestDTO.getAssigneeId());
        // System.out.println("ProjectId: " + createRequestDTO.getProjectId());


            Issue issue = new Issue();
            issue.setTitle(createRequestDTO.getTitle());
            issue.setDescription(createRequestDTO.getDescription());
            issue.setIssueType(createRequestDTO.getIssueType());
            issue.setIssuePriority(createRequestDTO.getIssuePriority());
            issue.setIssueStatus(createRequestDTO.getIssueStatus());
            issue.setAssignee(assignUser);
            issue.setReporter(reportUser);
            issue.setProject(project);
            issue.setStoryPoints(createRequestDTO.getStoryPoints());
            issue.setDueDate(createRequestDTO.getDueDate());
            issue.setCreatedAt(LocalDateTime.now());
            issue.setUpdatedAt(LocalDateTime.now());

        issueRepository.save(issue);
        notificationService.sendNotification(assignUser.getId(), new NotificationSendDTO("You have been assigned to issue "+ issue.getTitle() + " in project " + project.getName()));
       emailService.sendEmail(
    assignUser.getEmail(),
    "New Issue Assigned – " + issue.getTitle(),
    "Hello " + assignUser.getUsername() + ",\n\n"
    + "You have been assigned a new issue in MiniJira.\n\n"
    + "Issue Details:\n"
    + "Title: " + issue.getTitle() + "\n"
    + "Project: " + project.getName() + "\n"
    + "Assigned By: " + project.getUser().getUsername() + "\n\n"
    + "Please review the issue and start working on it at your earliest convenience.\n\n"
    + "If you have any questions, feel free to reach out.\n\n"
    + "Best regards,\n"
    + "MiniJira Team"
);

            return new CreateResponseDTO(
                issue.getId(),
                issue.getTitle(),
                project.getName(),
                assignUser.getUsername(),
                reportUser.getUsername()
            );
        


    }

    public  IssueResponseDTO getIssueDetails(Long id) {
        Issue issue= issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue Not Found!"));

                return new IssueResponseDTO(issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getIssueType().name(),
                issue.getIssuePriority().name(),
                issue.getIssueStatus().name(),
                issue.getAssignee().getUsername(),
                issue.getReporter().getUsername(),
                issue.getProject().getName(),
                issue.getStoryPoints(),
                issue.getDueDate().toString(),
                issue.getCreatedAt().toString()
                );
       
    }

    
       
        
    public List<IssueResponseDTO> getIssuesByProjectId(Long projectId) {

    List<Issue> issues = issueRepository.findAllByProjectId(projectId)
            .orElseThrow(() -> new RuntimeException("Project Not Found!"));

    return issues.stream()
            .map(issue -> new IssueResponseDTO(
                    issue.getId(),
                    issue.getTitle(),
                    issue.getDescription(),
                    issue.getIssueType().name(),
                    issue.getIssuePriority().name(),
                    issue.getIssueStatus().name(),
                    issue.getAssignee().getUsername(),
                    issue.getReporter().getUsername(),
                    issue.getProject().getName(),
                    issue.getStoryPoints(),
                    issue.getDueDate().toString(),
                    issue.getCreatedAt().toString()
            ))
            .toList();
        }

    
    public String updateIssue(Long id, IssueRequestDTO request) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue Not Found!"));

        User assignee = userRepository.findByUsername(request.getAssigneeUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setIssueType(request.getIssueType());
        issue.setIssuePriority(request.getIssuePriority());
        issue.setIssueStatus(request.getIssueStatus());
        issue.setStoryPoints(request.getStoryPoints());
        issue.setDueDate(request.getDueDate());
        issue.setAssignee(assignee);
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);
        notificationService.sendNotification(assignee.getId(), new NotificationSendDTO("Issue has been updated in project " ));

        return "Issue updated successfully!" ;
    }

    
    public String deleteIssue(Long id) {
        
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue Not Found!"));
        issueRepository.deleteById(id);
        emailService.sendEmail(issue.getAssignee().getEmail(), "Issue Deleted", "The issue \"" + issue.getTitle() + "\" in project \"" + issue.getProject().getName() + "\" has been deleted.");
        return "Issue deleted successfully!" ;
    }

    public  String updateIssueStatus(Long id, UpdateStatusDTO updateStatusDTO) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue Not Found!"));

        issue.setIssueStatus(updateStatusDTO.getIssueStatus());
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);
        notificationService.sendNotification(issue.getAssignee().getId(), new NotificationSendDTO("Issue status has been updated in project " + issue.getProject().getName()));
        emailService.sendEmail(issue.getAssignee().getEmail(), "Issue Status Updated", "The status of issue \"" + issue.getTitle() + "\" in project \"" + issue.getProject().getName() + "\" has been updated to " + updateStatusDTO.getIssueStatus().name() + ".");
        return "Issue status updated successfully!" ;
        
    }

    public  String updateIssueAssignee(Long id, Long assigneeId) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue Not Found!"));
        notificationService.sendNotification(issue.getAssignee().getId(), new NotificationSendDTO("You have been unassigned from issue "+ issue.getTitle() + " in project " + issue.getProject().getName()));
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        issue.setAssignee(assignee);
        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);
        notificationService.sendNotification(assignee.getId(), new NotificationSendDTO("You have been assigned to issue "+ issue.getTitle() + " in project " + issue.getProject().getName()));
        emailService.sendEmail(assignee.getEmail(), "Issue Reassigned", "You have been assigned to issue " + issue.getTitle() + " in project " + issue.getProject().getName());
        emailService.sendEmail(issue.getAssignee().getEmail(), "Issue Unassigned", "You have been unassigned from issue " + issue.getTitle() + " in project " + issue.getProject().getName());
        return "Issue assignee updated successfully!" ;

    }

    




}
