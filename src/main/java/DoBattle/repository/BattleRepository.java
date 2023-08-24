package DoBattle.repository;

import DoBattle.domain.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {
    Battle findByBattleCode(String battleCode);

    List<Battle> findByCreateUserOrJoinUser(String identify, String identify1);
}

