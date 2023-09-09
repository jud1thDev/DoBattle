package DoBattle.controller;

import DoBattle.domain.Battle;
import DoBattle.domain.Percentage;
import DoBattle.domain.TodoData;
import DoBattle.domain.User;
import DoBattle.repository.PercentageRepository;
import DoBattle.service.BattleService;
import DoBattle.service.TodoDataService;
import DoBattle.service.joinBattleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    private TodoDataService todoDataService;

    @Autowired
    private PercentageRepository percentageRepository;


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

        List<String> partnerUsernames = battleService.getPartnerUsernames(joinedBattles, currentUser.getIdentify());
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
        model.addAttribute("battleName", battle.getBattleName());
        model.addAttribute("battleCategory", battle.getBattleCategory());
        model.addAttribute("startDate", battle.getStartDate());
        model.addAttribute("endDate", battle.getEndDate());
        model.addAttribute("battleCode", battle.getBattleCode());

        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);
        model.addAttribute("todoDataList", todoDataList);

        List<TodoData> currentUserTodoDataList = todoDataList.stream()
                .filter(todoData -> todoData.getUserIdentify().equals(currentUser.getIdentify()))
                .collect(Collectors.toList());

        List<String> partnerUsernames = battleService.getPartnerUsernames(Arrays.asList(battle), currentUser.getIdentify());
        model.addAttribute("partnerUsernames", partnerUsernames);

        // partnerUserPercentage.는 postmapping과 중복되니 별도 메서드로 뺴서.. 처리해야함(나중에
        Optional<Percentage> currentUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), LocalDate.now());
        Percentage currentUserPercentage = currentUserPercentageOptional.orElse(null);

        List<Percentage> partnerUserPercentages = new ArrayList<>();

        for (String partnerUserIdentify : partnerUsernames) {
            Optional<Percentage> partnerUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), LocalDate.now());
            partnerUserPercentageOptional.ifPresent(partnerUserPercentages::add);
        }

        model.addAttribute("currentUserPercentage", currentUserPercentage);
        model.addAttribute("partnerUserPercentages", partnerUserPercentages);


        return "battleDetail";
    }

    @PostMapping("/battle/detail")
    public String showDetailPage2(@RequestParam String battleCode,
                                            @RequestParam String todoDataValue,
                                            @RequestParam String value,
                                            HttpSession session,
                                            Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        Battle battle = battleService.getBattleByCode(battleCode);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("battleName", battle.getBattleName());
        model.addAttribute("battleCategory", battle.getBattleCategory());
        model.addAttribute("startDate", battle.getStartDate());
        model.addAttribute("endDate", battle.getEndDate());
        model.addAttribute("battleCode", battle.getBattleCode());

        model.addAttribute("todoDataValue", todoDataValue);
        model.addAttribute("value", value);

        Percentage percentage = new Percentage();
        percentage.setBattle(battle);
        percentage.setUserIdentify(currentUser.getIdentify());
        percentage.setDate(LocalDate.now());
        percentage.setAchievementRate(0.0);

        percentageRepository.save(percentage);

        // 각 정보들 불러오는 로직
        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);
        model.addAttribute("todoDataList", todoDataList);

        List<String> partnerUsernames = battleService.getPartnerUsernames(Arrays.asList(battle), currentUser.getIdentify());
        model.addAttribute("partnerUsernames", partnerUsernames);

        Optional<Percentage> currentUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), LocalDate.now());
        Percentage currentUserPercentage = currentUserPercentageOptional.orElse(null);

        List<Percentage> partnerUserPercentages = new ArrayList<>();

        for (String partnerUserIdentify : partnerUsernames) {
            Optional<Percentage> partnerUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), LocalDate.now());
            partnerUserPercentageOptional.ifPresent(partnerUserPercentages::add);
        }

        model.addAttribute("currentUserPercentage", currentUserPercentage);
        model.addAttribute("partnerUserPercentages", partnerUserPercentages);

        return "battleDetail";
    }


    @GetMapping("/calender/detail")
    public String showCalenderDetail(@RequestParam String battleCode,
                                     HttpSession session,
                                     Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        Battle battle = battleService.getBattleByCode(battleCode);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("battleName", battle.getBattleName());
        model.addAttribute("battleCategory", battle.getBattleCategory());
        model.addAttribute("startDate", battle.getStartDate());
        model.addAttribute("endDate", battle.getEndDate());
        model.addAttribute("battleCode", battle.getBattleCode());

        // TodoData를 불러오는 로직 추가
        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);
        model.addAttribute("todoDataList", todoDataList);

        List<String> partnerUsernames = battleService.getPartnerUsernames(Arrays.asList(battle), currentUser.getIdentify());
        model.addAttribute("partnerUsernames", partnerUsernames);

        return "calender";
    }
}