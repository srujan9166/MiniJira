package com.example.minijira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.commentDTO.CommentRequestDTO;
import com.example.minijira.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/issues/{id}/comments")
    public ResponseEntity<?> addCommentToIssue(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addCommentToIssue(id, commentRequestDTO));
    }

    @GetMapping("/issues/{id}/comments")
    public ResponseEntity<?> getCommentsByIssueId(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getCommentsByIssueId(id));
    }   

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO){
       
        return ResponseEntity.ok().body(commentService.updateComment(id, commentRequestDTO));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    


}
