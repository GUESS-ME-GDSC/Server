package gdsc.mju.guessme.domain.user.repository;

import gdsc.mju.guessme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // User findByUserId(String userId);

    Optional<User> findByUserId(String userId);
}
