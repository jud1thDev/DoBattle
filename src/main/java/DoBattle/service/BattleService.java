package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.domain.User;
import DoBattle.repository.BattleRepository;
import DoBattle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Random;

@Service
public class BattleService {

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

    public Battle joinBattle(String battleCode, String identify) {
        Battle battle = battleRepository.findByBattleCode(battleCode);

        if (battle != null && battle.getJoinUser() == null) {
            battle.setJoinUser(identify);
            return battleRepository.save(battle);
        }

        return null;
    }
}
