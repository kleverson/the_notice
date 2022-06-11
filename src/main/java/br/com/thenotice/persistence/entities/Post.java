package br.com.thenotice.persistence.entities;

import br.com.thenotice.persistence.enums.PostStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class Post extends Base{

    private String title;
    private String slug;
    private String excerpt;
    private String content;

    @ManyToOne
    private Image image;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_interest",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToOne
    private User author;
}
