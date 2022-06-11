package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.InterestPostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.domain.response.GenericMessage;
import br.com.thenotice.persistence.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/tag")
@RestController
public class TagController {
    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity listAll(@RequestParam(value = "term", required = false, defaultValue = "") String term){
        return ResponseEntity.ok().body(this.tagService.getAll(term));
    }

    @PostMapping
    public ResponseEntity addItem(@Valid @RequestBody InterestPostBody interestPostBody){
        GenericData interest = this.tagService.addItem(interestPostBody);

        if(null != interest.getData()){
            return ResponseEntity.ok().body(interest.getData());
        }else{
            return new ResponseEntity(new GenericMessage(interest.getMsg()), interest.getStatus());
        }
    }
}
