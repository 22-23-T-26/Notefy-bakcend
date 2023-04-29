package mk.ukim.finki.notefy.repository;

import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.model.entities.Category;
import mk.ukim.finki.notefy.model.entities.Post;
import mk.ukim.finki.notefy.model.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
     List<Post> findAllBySubject(Subject subject);
     List<Post> findAllByCreatedBy(AppUser appUser);
     List<Post> findAllByCategory(Category category);
     List<Post> findAllByDateOfCreation(Date date);
     List<Post> findAllByFinalRating(Double rating);
     List<Post> findAllByFinalRatingBefore(Double rating);
     List<Post> findAllByFinalRatingAfter(Double rating);




}
