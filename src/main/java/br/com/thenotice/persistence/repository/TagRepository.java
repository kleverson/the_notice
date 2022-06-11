package br.com.thenotice.persistence.repository;

import br.com.thenotice.persistence.entities.Interest;
import br.com.thenotice.persistence.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);

    Tag findBySlug(String slug);

    int countBySlugEquals(String slug);

    @Query(value="SELECT * FROM Tag t where t.name ILIKE %:name%", nativeQuery = true)
    List<Tag> listByTerm(String name);

    @Query(value = "SELECT * FROM Tag LIMIT ?1", nativeQuery = true)
    List<Tag> listTop(int limit);

}

