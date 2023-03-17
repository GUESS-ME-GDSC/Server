package gdsc.mju.guessme.domain.info.repository;

import gdsc.mju.guessme.domain.info.entity.Info;
import gdsc.mju.guessme.domain.person.entity.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepository extends JpaRepository<Info, Long> {

    List<Info> findAllByPerson(Person person);
}
