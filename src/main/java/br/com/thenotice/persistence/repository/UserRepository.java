package br.com.thenotice.persistence.repository;

import br.com.thenotice.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    User findBySlug(String slug);

    @Query("SELECT u FROM users u where u.remember_token=?1")
    User userWhereToken(String token);

    int countBySlugEquals(String slug);

}
