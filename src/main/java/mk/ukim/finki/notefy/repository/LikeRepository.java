package mk.ukim.finki.notefy.repository;

import mk.ukim.finki.notefy.model.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
