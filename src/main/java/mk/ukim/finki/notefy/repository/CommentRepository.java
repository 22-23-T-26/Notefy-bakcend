package mk.ukim.finki.notefy.repository;


import mk.ukim.finki.notefy.model.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByDiscussionId(Long discussionId, Pageable pageable);
}
