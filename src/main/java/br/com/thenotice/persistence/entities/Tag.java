package br.com.thenotice.persistence.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class Tag extends Base{
    @Column(unique=true)
    private String name;
    private String slug;
}
