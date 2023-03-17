package gdsc.mju.guessme.domain.user.repository;

import gdsc.mju.guessme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

}
