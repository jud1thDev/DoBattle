package DoBattle.service;

import DoBattle.domain.Battle;
import DoBattle.domain.PartnerDTO;
import DoBattle.domain.Percentage;
import DoBattle.repository.PercentageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PercentageService {
    @Autowired
    PercentageRepository percentageRepository;
    @Autowired
    BattleService battleService;

    public double findCurrentUserPercent(Battle battle, String identify, LocalDate currentDate){
        Optional<Percentage> currentUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, identify, currentDate);
        //값이 있으면 들고오고, 아니면 0
        double currentUserPercent = currentUserPercentageOptional.isPresent() ? currentUserPercentageOptional.get().getAchievementRate() : 0.0;

        return currentUserPercent;
    }

    public List<PartnerDTO> getPartnerUserPercentages(Battle battle, String currentUserIdentify, List<String> partnerUserIdentifyList, LocalDate date) {
        List<Percentage> partnerUserPercentages = new ArrayList<>();

        for (String partnerUserIdentify : partnerUserIdentifyList) {
            Optional<Percentage> partnerUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, partnerUserIdentify, date);
            partnerUserPercentageOptional.ifPresent(partnerUserPercentages::add);
        }

        List<String> partnerUsernames = battleService.getPartnerUserName(Arrays.asList(battle), currentUserIdentify);

        List<PartnerDTO> partnerDTOs = new ArrayList<>();
        for (int i = 0; i < partnerUsernames.size(); i++) {
            String partnerUsername = partnerUsernames.get(i);
            Double partnerPercent = (i < partnerUserPercentages.size()) ? partnerUserPercentages.get(i).getAchievementRate() : 0.0;
            PartnerDTO partnerDTO = new PartnerDTO(partnerUsername, partnerPercent);
            partnerDTOs.add(partnerDTO);
        }

        return partnerDTOs;
    }
}
