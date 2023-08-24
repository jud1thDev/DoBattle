package DoBattle.controller;

import DoBattle.domain.Battle;
import DoBattle.domain.User;
import DoBattle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BattleController {

    @Autowired
    private BattleService battleService;

    @GetMapping("/makeBattle")
    public String showMakeBattlePage(HttpSession session, Model model) {
        // 현재 로그인된 유저의 정보를 세션에서 읽어옴(SpringConfig로 하는게 좋긴함..)
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);  // 사용자 정보를 모델에 추가
            return "makeBattle";
        }

        // 로그인 된 사용자가 없으면
        return "redirect:/login";
    }

    @PostMapping("/makeBattle")
    public String createBattle(@RequestParam String battleName,
                               @RequestParam String battleCategory,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               HttpSession session,
                               Model model) {

        // 현재 로그인된 유저의 정보를 세션에서 읽어옴(SpringConfig로 하는게 좋긴함..)
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            String identify = currentUser.getIdentify();

            Battle createdBattle = battleService.createBattle(battleName, battleCategory, startDate, endDate, identify);

            model.addAttribute("battleCode", createdBattle.getBattleCode());

            return "makeBattleSuccess";
        }

        // 로그인 된 사용자가 없으면
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

            Battle joinedBattle = battleService.joinBattle(battleCode, identify);

            if (joinedBattle != null) {
                model.addAttribute("joinedBattle", joinedBattle);
                return "joinBattleSuccess";
            } else {
                model.addAttribute("joinBattleError", "배틀 참여에 실패했습니다. 이미 참여된 배틀이거나 잘못된 코드일 수 있습니다.");
                return "makeBattle";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/battle")
    public String showBattlePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            String identify = currentUser.getIdentify();

            List<Battle> joinedBattles = battleService.getJoinedBattles(identify);
            model.addAttribute("joinedBattles", joinedBattles);
            // joinedBattles는 createUser나 joinUser에 로그인된 유저와 동일한 identify가 존재해야함

            return "battle";
        }

        return "redirect:/login";
    }

    @GetMapping("/doingBattleList")
    public String showMainPage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login"; // 없으면 로그인 페이지로 이동
        }

        model.addAttribute("currentUser", currentUser);

        List<Battle> joinedBattles = battleService.getJoinedBattles(currentUser.getIdentify());
        model.addAttribute("joinedBattles", joinedBattles);
        // joinedBattles는 createUser나 joinUser에 로그인된 유저와 동일한 identify가 존재해야함

        return "doingBattleList";
    }

}
