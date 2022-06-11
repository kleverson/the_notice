package br.com.thenotice.controllers;

import br.com.thenotice.domain.request.LoginPostBody;
import br.com.thenotice.domain.response.AuthResponse;
import br.com.thenotice.domain.response.GenericMessage;
import br.com.thenotice.persistence.service.UserService;
import br.com.thenotice.security.JwtTokenUtil;
import br.com.thenotice.security.JwtUserDetailsService;
import br.com.thenotice.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginPostBody loginBody) throws Exception{
        authenticate(loginBody.getUsername(), loginBody.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginBody.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);


        if(!Helper.isNullOrEmpty(token)){
            AuthResponse response = new AuthResponse(userDetails, token, "Bearer");
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(new GenericMessage("Usuário e/ou senha incorretos"));
        }
    }


    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}
