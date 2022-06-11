package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.UserPostBody;
import br.com.thenotice.domain.response.GenericMessage;
import br.com.thenotice.persistence.entities.User;
import br.com.thenotice.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserPostBody userPostBody)
    {
        User check = this.userService.existUser(userPostBody.getUsername());
        if(null != check){
            return ResponseEntity.badRequest().body(new GenericMessage("This user exists on database"));
        }else{

            return ResponseEntity.ok().body(this.userService.addUser(userPostBody));
        }
    }

    @PutMapping("/activate")
    public ResponseEntity<?> registerUser(@RequestParam(value = "token", required = true, defaultValue = "") String token)
    {
        Boolean activate = this.userService.enableUser(token);

        if(activate){
            return ResponseEntity.ok().body(new GenericMessage("User activate"));
        }else{
            return ResponseEntity.badRequest().body(new GenericMessage("User not activate"));
        }
    }


}
