package br.com.thenotice.domain.response;

import br.com.thenotice.persistence.enums.PostInteractions;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InteractionResponse {
    PostInteractions interactions;
    int count;
}
