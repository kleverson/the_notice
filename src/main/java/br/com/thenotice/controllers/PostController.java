package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.IntereactionsPostBody;
import br.com.thenotice.domain.request.PostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.domain.response.GenericMessage;
import br.com.thenotice.persistence.entities.Post;
import br.com.thenotice.persistence.enums.PostStatus;
import br.com.thenotice.persistence.service.InteractionService;
import br.com.thenotice.persistence.service.PostService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/post")
@RestController
public class PostController {

    private PostService postService;
    private InteractionService interactionService;

    public PostController(PostService postService, InteractionService interactionService) {
        this.postService = postService;
        this.interactionService = interactionService;
    }

    @GetMapping
    public ResponseEntity listAll(
            @RequestParam(value = "term", required = false, defaultValue = "") String term,
            @RequestParam(value = "tag[]", required = false, defaultValue = "") String[] tag,
            @RequestParam(value = "interest[]", required = false, defaultValue = "") String[] interest,
            @RequestParam(value = "author", required = false, defaultValue = "") String author,
            Authentication authentication,
            @ParameterObject Pageable pageable
    ){
        Page<Post> results =  this.postService.getAll(term,tag, interest, author,authentication, pageable);

        return ResponseEntity.ok().body(results);
    }

    @GetMapping("show/{slug}")
    public ResponseEntity getPost(@PathVariable("slug") String slug){
        GenericData post = this.postService.getPost(slug);

        if(null != post.getData()){
            return ResponseEntity.ok().body(post.getData());
        }else{
            return new ResponseEntity(new GenericMessage(post.getMsg()), post.getStatus());
        }

    }

    @GetMapping("list-by")
    public ResponseEntity listAll(
            @RequestParam(value = "option", required = true, defaultValue = "") String option,
            @RequestParam(value = "value", required = true, defaultValue = "") String value,
            @ParameterObject Pageable pageable
    ){
        Page<Post> results =  this.postService.listBy(option, value, pageable);

        return ResponseEntity.ok().body(results);
    }

    @GetMapping("my-posts")
    public ResponseEntity listAll(
            @RequestParam(value = "term", required = false, defaultValue = "") String term,
            @RequestParam(value = "tag[]", required = false, defaultValue = "") String[] tag,
            @RequestParam(value = "interest[]", required = false, defaultValue = "") String[] interest,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @ParameterObject Pageable pageable,
            Authentication authentication
    ){
        Page<Post> results =  this.postService.myPosts(term, tag, interest,status, pageable, authentication);

        return ResponseEntity.ok().body(results);
    }

    @PutMapping("intereact")
    public ResponseEntity intereact(@Valid @RequestBody IntereactionsPostBody intereactionsPostBody,Authentication authentication){
        GenericData response = this.interactionService.makeInteraction(intereactionsPostBody, authentication);

        return new ResponseEntity(response.getData(), response.getStatus());
    }

    @PostMapping
    public ResponseEntity addItem(@Valid @RequestBody PostBody postBody, Authentication authentication){
        return ResponseEntity.ok().body(this.postService.addNew(postBody, authentication));
    }

}