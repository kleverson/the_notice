package br.com.thenotice.domain.request;

import br.com.thenotice.persistence.enums.PostInteractions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class IntereactionsPostBody {

    UUID post;
    PostInteractions postInteractions;

}
