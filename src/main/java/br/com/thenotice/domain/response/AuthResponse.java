package br.com.thenotice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class AuthResponse {
    UserDetails userDetails;
    String access_token;
    String type;
}
