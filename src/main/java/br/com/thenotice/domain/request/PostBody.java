package br.com.thenotice.domain.request;


import br.com.thenotice.persistence.enums.PostStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PostBody {
    @NotNull @NotEmpty String title;
     String slug;
     String excerpt;
    @NotNull @NotEmpty String content;
     List<FilePostBody> images;
    @NotNull PostStatus postStatus;
     List<String> interests;
     List<String> tags;
}
