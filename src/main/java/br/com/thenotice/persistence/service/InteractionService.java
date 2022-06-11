package br.com.thenotice.persistence.service;

import br.com.thenotice.domain.request.IntereactionsPostBody;
import br.com.thenotice.domain.response.GenericData;
import br.com.thenotice.domain.response.InteractionResponse;
import br.com.thenotice.persistence.entities.Interactions;
import br.com.thenotice.persistence.entities.User;
import br.com.thenotice.persistence.enums.PostInteractions;
import br.com.thenotice.persistence.repository.InteractionsRepository;
import br.com.thenotice.persistence.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InteractionService {

    InteractionsRepository interactionsRepository;
    UserRepository userRepository;

    public InteractionService(InteractionsRepository interactionsRepository, UserRepository userRepository) {
        this.interactionsRepository = interactionsRepository;
        this.userRepository = userRepository;
    }

    public GenericData makeInteraction(IntereactionsPostBody intereactionsPostBody, Authentication authentication){
        User currentUser = this.userRepository.findByUsername(authentication.getName());

        Interactions newInteractions = new Interactions();
        newInteractions.setPostId(intereactionsPostBody.getPost());
        newInteractions.setPostInteractions(intereactionsPostBody.getPostInteractions());
        newInteractions.setUserId(currentUser.getUuid());

        this.interactionsRepository.save(newInteractions);

        return new GenericData(HttpStatus.OK, null, this.getData(intereactionsPostBody.getPost()));

    }

    public List<InteractionResponse> getData(UUID postId)
    {
        List<InteractionResponse> interactionResponses = new ArrayList<>();

        int interactionsView = this.interactionsRepository.countByPostIdAndPostInteractionsEquals(postId, PostInteractions.VIEW);
        int interactionsLike = this.interactionsRepository.countByPostIdAndPostInteractionsEquals(postId, PostInteractions.LIKE);
        int interactionsUnlike = this.interactionsRepository.countByPostIdAndPostInteractionsEquals(postId, PostInteractions.UNLIKE);

        interactionResponses.add(new InteractionResponse(PostInteractions.VIEW, interactionsView));
        interactionResponses.add(new InteractionResponse(PostInteractions.LIKE, interactionsLike));
        interactionResponses.add(new InteractionResponse(PostInteractions.UNLIKE, interactionsUnlike));

        return interactionResponses;

    }
}
