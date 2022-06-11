package br.com.thenotice.persistence.repository;


import br.com.thenotice.persistence.entities.Post;
import br.com.thenotice.persistence.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findByPostStatus(PostStatus status, Pageable pageable);
    int countBySlugEquals(String slug);

    Post findBySlug(String slug);
}
