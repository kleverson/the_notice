package br.com.thenotice.persistence.entities;

import br.com.thenotice.persistence.enums.PostInteractions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class Interactions extends Base{

    UUID postId;
    @Enumerated(EnumType.STRING)
    PostInteractions postInteractions;
    UUID userId;
}
