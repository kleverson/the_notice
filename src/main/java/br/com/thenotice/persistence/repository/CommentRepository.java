package br.com.thenotice.persistence.repository;

import br.com.thenotice.persistence.entities.Comment;
import br.com.thenotice.persistence.enums.CommentStatus;
import br.com.thenotice.persistence.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    int countByPost(UUID uuid);

    Page<Comment> findByPostAndStatus(UUID postId, CommentStatus postStatus, Pageable pageable);
}
