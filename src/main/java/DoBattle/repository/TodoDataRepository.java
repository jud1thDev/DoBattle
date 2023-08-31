package DoBattle.repository;

import DoBattle.domain.TodoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoDataRepository extends JpaRepository<TodoData, Long> {

    List<TodoData> findByUserIdentify(String userIdentify);
    TodoData findByBattle_BattleCode(String battleCode);
    long countByCompletedCount(int completedCount);
    long countByIncompletedCount(int incompletedCount);

}
