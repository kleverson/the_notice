package br.com.thenotice.persistence.repository;

import br.com.thenotice.persistence.entities.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PreferenceRepository extends JpaRepository<UserPreferences, UUID>
{
    List<UserPreferences> findByUserId(UUID user);
}
