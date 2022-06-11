package br.com.thenotice.persistence.repository;

import br.com.thenotice.persistence.entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
    Optional<Interest> findByName(String name);

    int countBySlugEquals(String slug);

    Interest findBySlug(String slug);

    @Query(value="SELECT * FROM Interest i where i.name ILIKE %:name%", nativeQuery = true)
    List<Interest> listByTerm(String name);

    @Query(value = "SELECT * FROM interest LIMIT ?1", nativeQuery = true)
    List<Interest> listTop(int limit);

}
