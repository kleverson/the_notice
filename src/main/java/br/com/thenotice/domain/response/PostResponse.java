package br.com.thenotice.domain.response;

import br.com.thenotice.persistence.entities.Image;
import br.com.thenotice.persistence.entities.Interest;
import br.com.thenotice.persistence.entities.Tag;
import br.com.thenotice.persistence.entities.User;
import br.com.thenotice.persistence.enums.PostStatus;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private UUID uuid;
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private Image image;
    private PostStatus postStatus;
    private Set<Interest> interests = new HashSet<>();
    private Set<Tag> tags = new HashSet<>();
    private User author;
    private List<InteractionResponse> interactions = new ArrayList<>();
    private int comment;
}
