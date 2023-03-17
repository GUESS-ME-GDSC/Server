package gdsc.mju.guessme.domain.info.repository;

import gdsc.mju.guessme.domain.info.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepository extends JpaRepository<Info, Long> {
}
