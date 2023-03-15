package gdsc.mju.guessme.domain.person.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import gdsc.mju.guessme.domain.person.entity.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
