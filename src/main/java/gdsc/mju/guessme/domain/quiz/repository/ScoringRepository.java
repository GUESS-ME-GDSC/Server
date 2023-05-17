package gdsc.mju.guessme.domain.quiz.repository;

import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.quiz.entity.Scoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoringRepository extends JpaRepository<Scoring, Long> {
    boolean existsByQuestionAndPerson(String question, Person person);
    Scoring findByQuestionAndPerson(String question, Person person);
    void deleteByQuestionAndPerson(String question, Person person);
}