package DoBattle.repository;

import DoBattle.domain.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle, Long> {
    Battle findBybattleCode(String battleCode);
}

