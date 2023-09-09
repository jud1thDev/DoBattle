package DoBattle.repository;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoDataRepository extends JpaRepository<TodoData, Long> {

    List<TodoData> findByUserIdentify(String userIdentify);
    List<TodoData> findByBattle_BattleCode(String battleCode);
    // 만약 findByBattleCode 로 한다면 'code'라는 필드(파라미터)명이 존재해야해서 오류가 떴음.

    // 퍼센트 - 완료된 개수 계산
    int countByBattleAndUserIdentifyAndValue(Battle battle, String userIdentify, String value);

    // 퍼센트 - 전체 투두 개수 계산
    int countByBattleAndUserIdentify(Battle battle, String userIdentify);

    List<TodoData> findByBattleAndUserIdentify(Battle battle, String identify);
    // 하.. 이거 중복 메서드라 코드정리 필요해보이긴 한데 일단.... 대기
}
