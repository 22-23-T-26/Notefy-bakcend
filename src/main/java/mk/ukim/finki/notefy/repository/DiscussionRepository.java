package mk.ukim.finki.notefy.repository;

import mk.ukim.finki.notefy.model.entities.Discussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Page<Discussion> findByTitleContainingOrderByCreatedTimeDesc(String title, Pageable pageable);
}