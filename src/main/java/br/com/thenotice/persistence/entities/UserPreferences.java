package br.com.thenotice.persistence.entities;

import br.com.thenotice.persistence.enums.PreferenceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Entity
public class UserPreferences extends Base{

    private UUID itemId;

    @Enumerated(EnumType.STRING)
    private PreferenceType type;

    private UUID userId;
}
