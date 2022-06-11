package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.CommentPostBody;
import br.com.thenotice.persistence.entities.Comment;
import br.com.thenotice.persistence.service.CommentService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity addItem(@Valid @RequestBody CommentPostBody commentPostBody, Authentication authentication){
       return ResponseEntity.ok().body(this.commentService.addItem(commentPostBody, authentication));
    }

    @GetMapping("/{postId}")
    public ResponseEntity getComments(@PathVariable("postId") UUID postId, @ParameterObject Pageable pageable){
        return ResponseEntity.ok().body(this.commentService.getComment(postId, pageable));
    }

}
