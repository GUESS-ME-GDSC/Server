package gdsc.mju.guessme.domain.person.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.mju.guessme.domain.person.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByFavoriteTrue();

    List<Person> findByFavoriteFalse();
}
