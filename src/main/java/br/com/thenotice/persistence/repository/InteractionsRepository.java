package br.com.thenotice.persistence.repository;

import br.com.thenotice.persistence.entities.Interactions;
import br.com.thenotice.persistence.enums.PostInteractions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InteractionsRepository extends JpaRepository<Interactions, UUID> {

    int countByPostIdAndPostInteractionsEquals(UUID post, PostInteractions interactions);

    @Query(value = "SELECT count(*) as count, post_interactions as interact \n" +
            "FROM public.interactions where post_id = :post \n" +
            "group by post_interactions", nativeQuery = true)
    List<Interactions> listInteractions(UUID post);
}
