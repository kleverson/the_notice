package br.com.thenotice.persistence.entities;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity(name = "users")
public class User extends Base {

    private  String name;

    @Column(unique=true)
    private String username;
    private String password;
    private String remember_token;

    private String slug;
}
