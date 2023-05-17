package gdsc.mju.guessme.domain.quiz.repository;

import gdsc.mju.guessme.domain.quiz.entity.Scoring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoringRepository extends JpaRepository<Scoring, Long> {
    boolean existsByInfoId(Long infoId);
    Scoring findByInfoId(Long infoId);
    void deleteByInfoId(Long infoId);
}