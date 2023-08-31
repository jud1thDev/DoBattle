package DoBattle.repository;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoDataRepository extends JpaRepository<TodoData, Long> {

    List<TodoData> findByUserIdentify(String userIdentify);
    TodoData findByBattle_BattleCode(String battleCode);
    int countByCompletedCount(int completedCount);
    int countByIncompleteCount(int incompleteCount);
}
