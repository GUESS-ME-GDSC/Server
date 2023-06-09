package gdsc.mju.guessme.domain.person.repository;

import java.util.List;
import javax.transaction.Transactional;

import gdsc.mju.guessme.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.mju.guessme.domain.person.entity.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFavoriteAndUser_UserId(boolean favorite, String userId);

    List<Person> findByUser(User user);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Person p set p.score=:score where p.id=:personId")
    void updateScore(@Param("personId") Long personId, @Param("score") Long score);

    @Modifying
    @Transactional
    @Query("update Person e set e.favorite = case when e.favorite = true then false else true end where e.id = ?1")
    void toggleFavorite(Long id);
}
