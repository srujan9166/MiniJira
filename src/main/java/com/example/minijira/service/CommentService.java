package com.example.minijira.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.example.minijira.dto.commentDTO.CommentRequestDTO;
import com.example.minijira.dto.commentDTO.CommentResponseDTO;
import com.example.minijira.model.Comment;
import com.example.minijira.model.Issue;
import com.example.minijira.model.User;
import com.example.minijira.repository.CommentRepository;
import com.example.minijira.repository.IssueRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final AuthServiceImpl authServiceImpl;
    public CommentService(CommentRepository commentRepository , IssueRepository issueRepository, AuthServiceImpl authServiceImpl){
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.authServiceImpl = authServiceImpl;
    }

    public  CommentResponseDTO addCommentToIssue(Long id, CommentRequestDTO commentRequestDTO) {
        Issue issue = issueRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Issue Not Found!"));
            User user = authServiceImpl.getCurrentUser();

        Comment comment = Comment.builder()
                    .content(commentRequestDTO.getContent())
                    .author(user)
                    .issue(issue)
                    .createdAt(LocalDateTime.now())
                    .build();
        commentRepository.save(comment);
        return convertCommentToDTO(comment);
       
       
    }

    public CommentResponseDTO convertCommentToDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorUsername(comment.getAuthor().getUsername())
                .issueId(comment.getIssue().getId())
                .build();
    }

    public  List<CommentResponseDTO> getCommentsByIssueId(Long id) {
        Issue issue = issueRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Issue Not Found!"));
        List<Comment> comments = commentRepository.findByIssue(issue)
                    .orElseThrow(() -> new RuntimeException("Comments Not Found!"));
        return comments.stream()
                .map(this::convertCommentToDTO)
                .toList();

        
    }

    public String updateComment(Long id, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment Not Found!"));
        // User user = authServiceImpl.getCurrentUser();
        // if(!comment.getAuthor().getId().equals(user.getId())){
        //     throw new RuntimeException("Unauthorized to update this comment!");
        // }
        comment.setContent(commentRequestDTO.getContent());
        commentRepository.save(comment);
        return "Comment Updated Successfully!";
       
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment Not Found!"));
        // User user = authServiceImpl.getCurrentUser();
        // if(!comment.getAuthor().getId().equals(user.getId())){
        //     throw new RuntimeException("Unauthorized to delete this comment!");
        // }
        commentRepository.delete(comment);
    }

    




}
