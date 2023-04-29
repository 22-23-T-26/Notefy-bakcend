package mk.ukim.finki.notefy.repository;

import mk.ukim.finki.notefy.model.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

//    Optional<AppUser> findByUsernameAndPassword(String username, String password);

    Optional<AppUser> findByUsernameOrEmailAndPassword(String username, String email, String password);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);
}