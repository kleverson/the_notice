package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.CommentPostBody;
import br.com.thenotice.persistence.entities.Comment;
import br.com.thenotice.persistence.entities.User;
import br.com.thenotice.persistence.enums.CommentStatus;
import br.com.thenotice.persistence.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;


    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Comment addItem(CommentPostBody commentPostBody, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        Comment newcommnet = new Comment();
        newcommnet.setPost(commentPostBody.getPostId());
        newcommnet.setText(commentPostBody.getText());
        newcommnet.setStatus(CommentStatus.NEW);
        newcommnet.setUser(user);

        this.commentRepository.save(newcommnet);

        return newcommnet;
    }

    public Page<Comment> getComment(UUID postId, Pageable pageable){
        return this.commentRepository.findByPostAndStatus(postId, CommentStatus.APPROVED, pageable);
    }

    public int commnetCount(UUID postId){
        return this.commentRepository.countByPost(postId);
    }
}
