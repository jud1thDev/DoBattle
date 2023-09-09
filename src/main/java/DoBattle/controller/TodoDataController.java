package DoBattle.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import DoBattle.domain.*;
import DoBattle.repository.*;
import DoBattle.service.TodoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/battle")
public class TodoDataController {

    @Autowired
    private TodoDataService todoDataService;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private TodoDataRepository todoDataRepository;

    @Autowired
    private PercentageRepository percentageRepository;

    @PostMapping("/{battleCode}/saveTodoData")
    public RedirectView saveTodoData(@PathVariable String battleCode,
                                     @RequestParam String todoDataValue,
                                     @RequestParam String value,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session) {

        Battle battle = battleRepository.findByBattleCode(battleCode);
        User currentUser = (User) session.getAttribute("currentUser");

        // TodoData를 저장
        TodoData todoData = todoDataService.saveTodoData(battle, currentUser.getIdentify(), todoDataValue, value, session);

        // Percentage 엔티티를 조회하여 업데이트 또는 생성
        Optional<Percentage> existingPercentage = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, currentUser.getIdentify(), LocalDate.now());

        double achievementRate = calculateAchievementRate(todoDataRepository.findByBattleAndUserIdentify(battle, currentUser.getIdentify()), "done");

        Percentage percentage;
        if (existingPercentage.isPresent()) {
            percentage = existingPercentage.get();
        } else {
            percentage = new Percentage();
            percentage.setBattle(battle);
            percentage.setUserIdentify(currentUser.getIdentify());
        }

        percentage.setDate(LocalDate.now());
        percentage.setAchievementRate(achievementRate);

        percentageRepository.save(percentage);

        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);
        redirectAttributes.addFlashAttribute("todoDataList", todoDataList);
        String redirectUrl = "/battle/detail?battleCode=" + battleCode;

        return new RedirectView(redirectUrl);
    }

    private double calculateAchievementRate(List<TodoData> todoDataList, String valueDone) {
        int completedCount = 0;
        int totalTodoCount = 0;

        for (TodoData todoData : todoDataList) {
            if (valueDone.equals(todoData.getValue())) {
                completedCount++;
            }
            totalTodoCount++;
        }

        return (totalTodoCount > 0) ? (completedCount * 100.0) / totalTodoCount : 0.0;
    }

    @PostMapping("/{battleCode}/updateTodoData")
    public RedirectView updateTodoData(@PathVariable String battleCode,
                                       @RequestParam Long todoDataId,
                                       @RequestParam String value,
                                       RedirectAttributes redirectAttributes,
                                       HttpSession session) {

        Battle battle = battleRepository.findByBattleCode(battleCode);

        TodoData todoData = todoDataRepository.findById(todoDataId).orElse(null);

        if (todoData == null) {
            String redirectUrl = "/battle/detail?battleCode=" + battleCode;
            return new RedirectView(redirectUrl);
        }

        todoData.setValue(value);
        todoDataRepository.save(todoData);

        Optional<Percentage> existingPercentage = percentageRepository.findByBattleAndUserIdentifyAndDate(battle, todoData.getUserIdentify(), LocalDate.now());

        double achievementRate = calculateAchievementRate(todoDataRepository.findByBattleAndUserIdentify(battle, todoData.getUserIdentify()), "done");

        Percentage percentage;
        if (existingPercentage.isPresent()) {
            percentage = existingPercentage.get();
        } else {
            percentage = new Percentage();
            percentage.setBattle(battle);
            percentage.setUserIdentify(todoData.getUserIdentify());
        }

        percentage.setDate(LocalDate.now());
        percentage.setAchievementRate(achievementRate);

        percentageRepository.save(percentage);


        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);

        redirectAttributes.addFlashAttribute("todoDataList", todoDataList);

        String redirectUrl = "/battle/detail?battleCode=" + battleCode;

        return new RedirectView(redirectUrl);
    }
}
