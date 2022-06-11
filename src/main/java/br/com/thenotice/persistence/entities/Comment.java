package br.com.thenotice.persistence.entities;

import br.com.thenotice.persistence.enums.CommentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class Comment extends Base{

    private String text;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    UUID post;

    @OneToOne
    private User user;

}
