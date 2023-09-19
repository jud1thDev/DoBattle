package DoBattle.repository;

import DoBattle.domain.Battle;
import DoBattle.domain.Percentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PercentageRepository extends JpaRepository<Percentage, Long> {
    Optional<Percentage> findByBattleAndUserIdentifyAndDate(Battle battle, String identify, LocalDate date);
}

