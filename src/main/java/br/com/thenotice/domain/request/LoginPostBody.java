package br.com.thenotice.domain.request;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class LoginPostBody {
    @Email
    @NotNull
    @NotEmpty String username;
    @NotNull @NotEmpty String password;
}
