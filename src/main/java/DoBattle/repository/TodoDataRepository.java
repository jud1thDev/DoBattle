package DoBattle.repository;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TodoDataRepository extends JpaRepository<TodoData, Long> {

//    List<TodoData> findByUserIdentify(String userIdentify);
    List<TodoData> findByBattle_BattleCode(String battleCode);
    // 만약 findByBattleCode 로 한다면 'code'라는 필드(파라미터)명이 존재해야해서 오류가 떴음.

    List<TodoData> findByBattleAndUserIdentify(Battle battle, String identify);
    // 하.. 이거 중복 메서드라 코드정리 필요해보이긴 한데 일단.... 대기

    @Query(value = "SELECT * FROM todo_data WHERE battle_id = :battleId AND date = :date", nativeQuery = true)
    List<TodoData> findByBattleIdAndDate(Long battleId, LocalDate date);
    //오늘날짜에만 해당하는 모든 투두 들고오기

    @Query(value = "SELECT * FROM todo_data WHERE battle_id = :battleId AND date = :date AND user_identify = :identify", nativeQuery = true)
    List<TodoData> findByBattleIdAndDateAndUserIdentify(Long battleId, String identify, LocalDate date);
}
