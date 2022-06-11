package br.com.thenotice.domain.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class FilePostBody {
    private String name;
    private float size;
    private String mimetype;
}
