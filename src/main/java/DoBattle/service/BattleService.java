package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import DoBattle.domain.User;
import DoBattle.repository.BattleRepository;
import DoBattle.repository.TodoDataRepository;
import DoBattle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BattleService {

    private static final int MAX_CAPACITY = 2; // 최대 참가자 수 정의

    @Autowired
    private BattleRepository battleRepository;
    // 리포지토리 위에만 쓰는게 아니라 일케 주입도 해줘야함!!

    @Autowired
    private TodoDataRepository todoDataRepository;

    @Autowired
    private UserRepository userRepository;
    private Battle battleCode;

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
        return battle.getCurrentParticipants() >= MAX_CAPACITY;
    }

    public List<Battle> getJoinedBattles(String identify) {
        List<Battle> joinedBattles = battleRepository.findByCreateUserOrJoinUser(identify, identify);
        return joinedBattles;
    }

    // 배틀 상대 username 찾기(doingBattleList에서 필요)
    public String getUsernameBasedOnCondition(Battle battle, String currentUserIdentify) {
        String createUser = battle.getCreateUser();
        String joinUser = battle.getJoinUser();

        if (!createUser.equals(currentUserIdentify)) {
            return getUsernameByIdentify(createUser);
        } else {
            return getUsernameByIdentify(joinUser);
        }
    }

    private String getUsernameByIdentify(String identify) {
        User user = userRepository.findByIdentify(identify);
        if (user != null) {
            return "vs " + user.getUsername();
        } else {
            return "";
        }
    }

    public Battle getBattleByCode(String battleCode) {
        return battleRepository.findByBattleCode(battleCode);
    } // battledetail.html에 정보를 로드하기 위해 쓰임


    public List<String> getPartnerUsernames(List<Battle> battles, String currentUserIdentify) {
        List<String> partnerUsernames = new ArrayList<>();

        for (Battle battle : battles) {
            String partnerUsername = getUsernameBasedOnCondition(battle, currentUserIdentify);
            partnerUsernames.add(partnerUsername);
        }

        return partnerUsernames;
    }


}

