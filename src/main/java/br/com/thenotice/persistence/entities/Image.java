package br.com.thenotice.persistence.entities;

import br.com.thenotice.persistence.enums.ObjectType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class Image extends Base{
    private String name;
    private float size;

    @Enumerated(EnumType.STRING)
    private ObjectType type;
    private long relatedId;

    @Column(columnDefinition = "text")
    private String description;
}
