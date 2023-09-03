package DoBattle.repository;

import DoBattle.domain.TodoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoDataRepository extends JpaRepository<TodoData, Long> {

    List<TodoData> findByUserIdentify(String userIdentify);
    List<TodoData> findByBattle_BattleCode(String battleCode);
    // 만약 findByBattleCode 로 한다면 'code'라는 필드(파라미터)명이 존재해야해서 오류가 떴음.

}