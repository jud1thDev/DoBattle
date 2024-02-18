package DoBattle.controller;

import DoBattle.domain.*;
import DoBattle.repository.PercentageRepository;
import DoBattle.repository.UserRepository;
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
    private UserRepository userRepository;

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

        List<String> partnerUserIdentifyList = getPartnerUserIdentify(joinedBattles, currentUser.getIdentify());
        List<String> partnerUsernames = new ArrayList<>();

        for (String partnerUserIdentify : partnerUserIdentifyList) {
            String partnerUsername = battleService.getUsernameByIdentify(partnerUserIdentify);
            partnerUsernames.add(partnerUsername);
        }

        model.addAttribute("partnerUsernames", partnerUsernames);

        return "doingBattleList";
    }

    private List<String> getPartnerUserIdentify(List<Battle> battles, String currentUserIdentify) {
        List<String> partnerUserIdentifyList = new ArrayList<>();

        for (Battle battle : battles) {
            String partnerUserIdentify = getPartnerUserIdentifyBasedOnCondition(battle, currentUserIdentify);
            partnerUserIdentifyList.add(partnerUserIdentify);
        }

        return partnerUserIdentifyList;
    }

    public String getPartnerUserIdentifyBasedOnCondition(Battle battle, String currentUserIdentify) {
        String createUser = battle.getCreateUser();
        String joinUser = battle.getJoinUser();

        if (!createUser.equals(currentUserIdentify)) {
            return createUser;
        } else {
            return joinUser;
        }
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

        //파트너 list 찾기
        List<String> partnerUserIdentifyList = getPartnerUserIdentify(Arrays.asList(battle), currentUser.getIdentify());
        List<String> partnerUsernames = new ArrayList<>();

        for (String partnerUserIdentify : partnerUserIdentifyList) {
            String partnerUsername = battleService.getUsernameByIdentify(partnerUserIdentify);
            partnerUsernames.add(partnerUsername);
        }
        model.addAttribute("partnerUsernames", partnerUsernames);

        //본인 퍼센트 찾기
        LocalDate currentDate = LocalDate.now();
        Optional<Percentage> currentUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), currentDate);
        Percentage currentUserPercentage = currentUserPercentageOptional.orElse(null);

        //상대방 퍼센트 찾기
        List<Percentage> partnerUserPercentages = getPartnerUserPercentages(battle, partnerUserIdentifyList, currentDate);

        model.addAttribute("currentUserPercentage", currentUserPercentage);
        model.addAttribute("partnerUserPercentages", partnerUserPercentages);

        List<PartnerDTO> partnerDTOs = new ArrayList<>();
        for (int i = 0; i < partnerUsernames.size(); i++) {
            String partnerUsername = partnerUsernames.get(i);
            Double partnerPercent = (i < partnerUserPercentages.size()) ? partnerUserPercentages.get(i).getAchievementRate() : null;
            PartnerDTO partnerDTO = new PartnerDTO(partnerUsername, partnerPercent);
            partnerDTOs.add(partnerDTO);
        }

        model.addAttribute("partnerDTOs", partnerDTOs);

//        System.out.println("partnerUserIdentifyList: " + partnerUserIdentifyList);
//        System.out.println("currentUserPercent: " + currentUserPercentage);
//        System.out.println("partnerUserPercent: " + partnerUserPercentages);
//        System.out.println("partnerUsername: " + partnerUsernames);

        return "battleDetail";
    }

    //투두 추가
//    @PostMapping("/battle/detail")
//    public String showDetailPage2(@RequestParam String battleCode,
//                                  @RequestParam String todoDataValue,
//                                  @RequestParam String value,
//                                  HttpSession session,
//                                  Model model) {
//        User currentUser = (User) session.getAttribute("currentUser");
//        Battle battle = battleService.getBattleByCode(battleCode);
//
//        Percentage percentage = new Percentage(battle, currentUser.getIdentify());
//        percentage.setAchievementRate(0.0); // 0.0 기본값
//        percentageRepository.save(percentage);
//
//        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);
//        model.addAttribute("todoDataList", todoDataList);
//
//        List<String> partnerUserIdentifyList = getPartnerUserIdentify(Arrays.asList(battle), currentUser.getIdentify());
//        List<String> partnerUsernames = new ArrayList<>();
//
//        for (String partnerUserIdentify : partnerUserIdentifyList) {
//            String partnerUsername = battleService.getUsernameByIdentify(partnerUserIdentify);
//            partnerUsernames.add(partnerUsername);
//        }
//
//        model.addAttribute("partnerUsernames", partnerUsernames);
//
//        LocalDate currentDate = LocalDate.now();
//        Optional<Percentage> currentUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), currentDate);
//        Percentage currentUserPercentage = currentUserPercentageOptional.orElse(null);
//
//        List<Percentage> partnerUserPercentages = getPartnerUserPercentages(battle, partnerUserIdentifyList, currentDate);
//
//        model.addAttribute("currentUserPercentage", currentUserPercentage);
//        model.addAttribute("partnerUserPercentages", partnerUserPercentages);
//
//        List<PartnerDTO> partnerDTOs = new ArrayList<>();
//        for (int i = 0; i < partnerUsernames.size(); i++) {
//            String partnerUsername = partnerUsernames.get(i);
//            Double partnerPercent = (i < partnerUserPercentages.size()) ? partnerUserPercentages.get(i).getAchievementRate() : null;
//            PartnerDTO partnerDTO = new PartnerDTO(partnerUsername, partnerPercent);
//            partnerDTOs.add(partnerDTO);
//        }
//
//        model.addAttribute("partnerDTOs", partnerDTOs);
//
//        return "battleDetail";
//    }

    private List<Percentage> getPartnerUserPercentages(Battle battle, List<String> partnerUserIdentifyList, LocalDate date) {
        List<Percentage> partnerUserPercentages = new ArrayList<>();

        for (String partnerUserIdentify : partnerUserIdentifyList) {
            Optional<Percentage> partnerUserPercentageOptional = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, partnerUserIdentify, date);
            partnerUserPercentageOptional.ifPresent(partnerUserPercentages::add);

        }

        return partnerUserPercentages;
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

/*        List<String> partnerUsernames = battleService.getPartnerUsernames(Arrays.asList(battle), currentUser.getIdentify());
        model.addAttribute("partnerUsernames", partnerUsernames);*/

        return "calender";
    }
}
