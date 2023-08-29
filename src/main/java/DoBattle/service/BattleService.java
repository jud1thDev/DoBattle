package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class BattleService {

    private static final int MAX_CAPACITY = 2; // 최대 참가자 수 정의

    @Autowired
    private BattleRepository battleRepository;

    public Battle createBattle(String battleName,
                               String battleCategory,
                               String startDate,
                               String endDate,
                               String identify) {

        String battleCode = generateUniqueBattleCode();

        Battle newBattle = new Battle();
        newBattle.setBattleName(battleName);
        newBattle.setBattleCategory(battleCategory);
        newBattle.setStartDate(LocalDate.parse(startDate));
        newBattle.setEndDate(LocalDate.parse(endDate));
        newBattle.setBattleCode(battleCode);
        newBattle.setCreateUser(identify);

        return battleRepository.save(newBattle);
    }

    private String generateUniqueBattleCode() {
        Random random = new Random();
        String battleCode = null;
        boolean isCodeUnique = false;

        while (!isCodeUnique) {
            int codeValue = 100000 + random.nextInt(900000);
            battleCode = String.valueOf(codeValue);

            // 중복검사
            Battle existingBattle = battleRepository.findByBattleCode(battleCode);
            if (existingBattle == null) {
                isCodeUnique = true;
            }
        }

        return battleCode;
    }

    public joinBattleResult joinBattle(String battleCode, String identify) {
        Battle battle = battleRepository.findByBattleCode(battleCode);

        if (battle == null) {
            return joinBattleResult.INVALID_CODE;
        }

        if (identify.equals(battle.getCreateUser()) || identify.equals(battle.getJoinUser())) {
            return joinBattleResult.ALREADY_JOINED;
        }

        if (isBattleFull(battle)) {
            return joinBattleResult.BATTLE_FULL;
        }

        battle.setJoinUser(identify);
        battle.setCurrentParticipants(battle.getCurrentParticipants() + 1);
        battleRepository.save(battle);

        return joinBattleResult.SUCCESS;
    }

    private boolean isBattleFull(Battle battle) {
        List<Battle> joinedBattles = battleRepository.findByCreateUserOrJoinUser(battle.getCreateUser(), battle.getJoinUser());
        return joinedBattles.size() >= MAX_CAPACITY;
    }

    public List<Battle> getJoinedBattles(String identify) {
        List<Battle> joinedBattles = battleRepository.findByCreateUserOrJoinUser(identify, identify);
        return joinedBattles;
    }
}

