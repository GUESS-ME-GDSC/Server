package gdsc.mju.guessme.domain.person.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.mju.guessme.domain.person.entity.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByFavoriteTrue();

    List<Person> findByFavoriteFalse();

    @Modifying
    @Transactional
    @Query("update Person e set e.favorite = case when e.favorite = true then false else true end where e.id = ?1")
    void toggleFavorite(Long id);
}
