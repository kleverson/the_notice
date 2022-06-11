package br.com.thenotice.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class UserPostBody {
    @NotNull @NotEmpty String name;
    @Email @NotNull @NotEmpty  String username;
    @NotNull @NotEmpty  String password;
}
