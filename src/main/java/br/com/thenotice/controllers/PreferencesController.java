package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.PreferencePostBody;
import br.com.thenotice.persistence.service.UserPreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("preferences")
@RestController
public class PreferencesController {

    private final UserPreferenceService preferenceService;

    public PreferencesController(UserPreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping
    public ResponseEntity get(Authentication authentication){
        return ResponseEntity.ok().body(this.preferenceService.getPrefs(authentication));
    }

    @PostMapping
    public ResponseEntity add(@Valid @RequestBody PreferencePostBody preferencePostBody, Authentication authentication){
        return ResponseEntity.ok().body(this.preferenceService.addPrefs(preferencePostBody, authentication));
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody PreferencePostBody preferencePostBody, Authentication authentication){
        return ResponseEntity.ok().body(this.preferenceService.update(preferencePostBody, authentication));
    }
}
