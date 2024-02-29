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
public class BattleController {
    @Autowired
    private BattleService battleService;

    @Autowired
    private TodoDataRepository todoDataRepository;

    @Autowired
    private PercentageService percentageService;

    @GetMapping("/makeBattle")
    public String showMakeBattlePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            return "makeBattle";
        }

        return "redirect:/login";
    }

    @PostMapping("/makeBattle")
    public String createBattle(@RequestParam String battleName,
                               @RequestParam String battleCategory,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               HttpSession session,
                               Model model) {

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            String identify = currentUser.getIdentify();

            Battle createdBattle = battleService.createBattle(battleName, battleCategory, startDate, endDate, identify);

            model.addAttribute("battleCode", createdBattle.getBattleCode());

            return "makeBattleSuccess";
        }

        return "redirect:/login";
    }

    @GetMapping("/joinBattle")
    public String showJoinBattlePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            return "joinBattle";
        }

        return "redirect:/login";
    }

    @PostMapping("/joinBattle")
    public String joinBattle(@RequestParam String battleCode,
                             HttpSession session,
                             Model model) {

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            String identify = currentUser.getIdentify();

            joinBattleResult result = battleService.joinBattle(battleCode, identify);

            if (result == joinBattleResult.SUCCESS) {
                return "joinBattleSuccess";
            } else if (result == joinBattleResult.ALREADY_JOINED) {
                model.addAttribute("joinBattleError", "이미 참여 중인 배틀입니다.");
            } else if (result == joinBattleResult.BATTLE_FULL) {
                model.addAttribute("joinBattleError", "참가 정원이 가득찬 배틀입니다.");
            } else if (result == joinBattleResult.INVALID_CODE) {
                model.addAttribute("joinBattleError", "잘못된 배틀 코드입니다. 다시 입력하세요.");
            }

            return "joinBattle";
        }

        return "redirect:/login";
    }

    @GetMapping("/doingBattleList")
    public String showMainPage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", currentUser);

        List<Battle> joinedBattles = battleService.getJoinedBattles(currentUser.getIdentify());
        model.addAttribute("joinedBattles", joinedBattles);

        List<String> partnerUsernames = battleService.getPartnerUserName(joinedBattles, currentUser.getIdentify());

        model.addAttribute("partnerUsernames", partnerUsernames);

        return "doingBattleList";
    }

    @GetMapping("/battle/detail")
    public String showDetailPage(@RequestParam String battleCode,
                                 HttpSession session,
                                 Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        Battle battle = battleService.getBattleByCode(battleCode);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("battle", battle);

        List<TodoData> todoDataList = todoDataRepository.findByBattleIdAndDate(battle.getId(), LocalDate.now());
        model.addAttribute("todoDataList", todoDataList);

        //파트너 list 찾기
        List<String> partnerUserIdentifyList = battleService.getPartnerUserIdentify(Arrays.asList(battle), currentUser.getIdentify());
        List<String> partnerUsernames = battleService.getPartnerUserName(Arrays.asList(battle), currentUser.getIdentify());
        model.addAttribute("partnerUsernames", partnerUsernames);

        //본인 퍼센트 찾기
        LocalDate currentDate = LocalDate.now();
        double currentUserPercentage = percentageService.findCurrentUserPercent(battle, currentUser.getIdentify(), currentDate);
        model.addAttribute("currentUserPercentage", currentUserPercentage);

        //상대방 퍼센트 찾기
        List<PartnerDTO> partnerDTOs = percentageService.getPartnerUserPercentages(battle, currentUser.getIdentify(), partnerUserIdentifyList, currentDate);
        model.addAttribute("partnerDTOs", partnerDTOs);

        return "battleDetail";
    }
}
