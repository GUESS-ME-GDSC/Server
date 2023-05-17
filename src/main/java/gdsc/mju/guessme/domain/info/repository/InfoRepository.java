package gdsc.mju.guessme.domain.info.repository;

import gdsc.mju.guessme.domain.info.entity.Info;
import gdsc.mju.guessme.domain.person.entity.Person;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InfoRepository extends JpaRepository<Info, Long> {

    List<Info> findAllByPerson(Person person);

    @Transactional
    @Modifying
    @Query("DELETE FROM Info e WHERE e.person.id = :personId AND e.id IN :ids")
    void deleteInfoByPersonIdAndInfoIds(
            @Param("personId") Long personId,
            @Param("ids") List<Long> ids
    );

    @Transactional
    void deleteAllInBatchByPersonId(Long personId);
}
