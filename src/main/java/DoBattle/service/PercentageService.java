import DoBattle.domain.Battle;
import DoBattle.domain.Percentage;
import DoBattle.domain.User;
import DoBattle.repository.PercentageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PercentageService {

    @Autowired
    private PercentageRepository percentageRepository;

    public void updateAchievementRate(Battle battle, User currentUser, LocalDate date, double newAchievementRate) {
        Optional<Percentage> percentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), date);

        if (percentageOptional.isPresent()) {
            Percentage percentage = percentageOptional.get();
            percentage.setAchievementRate(newAchievementRate);
            percentageRepository.save(percentage);
        }
    }
}
