package DoBattle.controller;

import DoBattle.domain.*;
import DoBattle.repository.PercentageRepository;
import DoBattle.repository.TodoDataRepository;
import DoBattle.repository.UserRepository;
import DoBattle.service.BattleService;
import DoBattle.service.PercentageService;
import DoBattle.service.TodoDataService;
import DoBattle.service.joinBattleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class CalenderController {
    @Autowired
    private BattleService battleService;

    @Autowired
    private PercentageService percentageService;

    @GetMapping("/calender/detail")
    public String showCalenderDetail(@RequestParam String battleCode,
                                     HttpSession session,
                                     Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        Battle battle = battleService.getBattleByCode(battleCode);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("battle", battle);

        //본인 퍼센트 찾기
        double currentUserPercentage = percentageService.findCurrentUserPercent(battle, currentUser.getIdentify(), LocalDate.now());
        model.addAttribute("currentUserPercentage", currentUserPercentage);

        //상대방 퍼센트 찾기
        List<String> partnerUserIdentifyList = battleService.getPartnerUserIdentify(Arrays.asList(battle), currentUser.getIdentify());
        List<PartnerDTO> partnerDTOs = percentageService.getPartnerUserPercentages(battle, currentUser.getIdentify(), partnerUserIdentifyList, LocalDate.now());
        model.addAttribute("partnerDTOs", partnerDTOs);

        return "calender";
    }

    @GetMapping("/calender/dateclick/{battleCode}/{clickDate}")
    @ResponseBody
    public Map<String, Object> clickCalenderDate(@PathVariable String battleCode, @PathVariable String clickDate,
                                                 HttpSession session,
                                                 Model model){
        Map<String, Object> responseData = new HashMap<>(); //반환해줄 값 (json으로 보내주기 위해)

        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        Battle battle = battleService.getBattleByCode(battleCode);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(clickDate, formatter);    //프론트에서 클릭한 날짜

        //본인 퍼센트 찾기
        double currentUserPercentage = percentageService.findCurrentUserPercent(battle, currentUser.getIdentify(), date);
        responseData.put("currentUserPercentage", currentUserPercentage);

        //상대방 퍼센트 찾기
        List<String> partnerUserIdentifyList = battleService.getPartnerUserIdentify(Arrays.asList(battle), currentUser.getIdentify());
        List<PartnerDTO> partnerDTOs = percentageService.getPartnerUserPercentages(battle, currentUser.getIdentify(), partnerUserIdentifyList, date);
        responseData.put("partnerDTOs", partnerDTOs);

        return responseData;
    }

    @GetMapping("/calender/fire/{battleCode}")
    @ResponseBody
    public Map<String, String> showFire(@PathVariable String battleCode, HttpSession session, Model model){
        Map<String, String> responseData = new HashMap<>(); //반환해줄 값 (json으로 보내주기 위해)

        User currentUser = (User) session.getAttribute("currentUser");
        Battle battle = battleService.getBattleByCode(battleCode);
        List<String> partnerUserIdentifyList = battleService.getPartnerUserIdentify(Arrays.asList(battle), currentUser.getIdentify());

        //시작-종료까지의 퍼센트 승리자 찾기
        LocalDate startDate = battle.getStartDate();
        LocalDate now = LocalDate.now();
        for (LocalDate date = startDate; date.isBefore(now.plusDays(1)); date = date.plusDays(1)) {
            //본인 퍼센트
            double currentUserPercentage = percentageService.findCurrentUserPercent(battle, currentUser.getIdentify(), date);
            //상대방 퍼센트 Max 찾기
            List<PartnerDTO> partnerDTOs = percentageService.getPartnerUserPercentages(battle, currentUser.getIdentify(), partnerUserIdentifyList, date);

            double maxPartnerUserPercentage = partnerDTOs.get(0).getPartnerPercent();
            String maxPartnerName = partnerDTOs.get(0).getPartnerUsername();
            for(int i=1; i < partnerDTOs.size(); i++){
                if(partnerDTOs.get(i).getPartnerPercent() >= maxPartnerUserPercentage) {
                    maxPartnerUserPercentage = partnerDTOs.get(i).getPartnerPercent();
                    maxPartnerName = partnerDTOs.get(i).getPartnerUsername();
                }
            }

            //계산
            String sendDate = date.toString();
            System.out.println(sendDate);
            if (currentUserPercentage > maxPartnerUserPercentage) {
                responseData.put(sendDate, "현재사용자");
            } else if(currentUserPercentage < maxPartnerUserPercentage){
                responseData.put(sendDate, "파트너");
            } else{
                responseData.put(sendDate, "동점");
            }
        }

        return responseData;
    }
}
