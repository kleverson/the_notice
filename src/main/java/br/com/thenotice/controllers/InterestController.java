package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.InterestPostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.domain.response.GenericMessage;
import br.com.thenotice.persistence.service.InterestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/interest")
@RestController
public class InterestController {

    private InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @GetMapping
    public ResponseEntity listAll(@RequestParam(value = "term", required = false, defaultValue = "") String term){
        return ResponseEntity.ok().body(this.interestService.getAll(term));
    }

    @PostMapping
    public ResponseEntity addItem(@Valid @RequestBody InterestPostBody interestPostBody){
        GenericData interest = this.interestService.addItem(interestPostBody);

        if(null != interest.getData()){
            return ResponseEntity.ok().body(interest.getData());
        }else{
            return new ResponseEntity(new GenericMessage(interest.getMsg()), interest.getStatus());
        }
    }
}
