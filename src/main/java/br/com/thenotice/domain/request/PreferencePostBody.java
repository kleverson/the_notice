package br.com.thenotice.domain.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PreferencePostBody {

    List<UUID> tags;
    List<UUID> interests;

}
