package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
public class BattleService {
    private final BattleRepository battleRepository;

    @Autowired
    public BattleService(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    @Transactional
    public Battle makeBattle(Battle battle) {
        return battleRepository.save(battle);
    }

    public boolean isCodeAvailable(String code) {
        return battleRepository.findByCode(code) == null;
    }

    public String generateUniqueBattleCode() {
        Random random = new Random();
        String battleCode;
        do {
            int randomNumber = 100000 + random.nextInt(900000);
            battleCode = String.valueOf(randomNumber);
        } while (!isCodeAvailable(battleCode));
        return battleCode;
    }
}

