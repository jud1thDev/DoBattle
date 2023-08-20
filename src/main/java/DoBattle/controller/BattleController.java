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
import java.time.LocalDate;

@Controller
public class BattleController {
    private final BattleService battleService;

    @Autowired
    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @GetMapping("/makeBattle")
    public String makeBattlePage() {
        return "makeBattle";
    }

    @PostMapping("/makeBattle")
    public String makeBattle(@RequestParam String battleName,
                             @RequestParam String battleCategory,
                             @RequestParam String startDate,
                             @RequestParam String endDate,
                             HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        Battle newBattle = new Battle();
        newBattle.setBattleName(battleName);
        newBattle.setBattleCategory(battleCategory);
        newBattle.setStartDate(LocalDate.parse(startDate));
        newBattle.setEndDate(LocalDate.parse(endDate));
        newBattle.setCreator(currentUser);

        String battleCode = battleService.generateUniqueBattleCode();
        newBattle.setCode(battleCode);

        battleService.makeBattle(newBattle);

        return "redirect:/makeBattleSuccess?battleCode=" + battleCode;
    }

    @GetMapping("/makeBattleSuccess")
    public String battleBattleSuccessPage(@RequestParam String battleCode, Model model) {
        model.addAttribute("battleCode", battleCode);
        return "makeBattleSuccess";
    }
}
