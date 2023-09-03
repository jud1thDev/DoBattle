package DoBattle.controller;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import DoBattle.repository.BattleRepository;
import DoBattle.repository.TodoDataRepository;
import DoBattle.service.TodoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/battle")
public class TodoDataController {

    @Autowired
    private TodoDataService todoDataService;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private TodoDataRepository todoDataRepository;

    @PostMapping("/{battleCode}/saveTodoData")
    public RedirectView saveTodoData(@PathVariable String battleCode,
                                     @RequestParam String todoDataValue,
                                     @RequestParam String value,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session) {

        Battle battle = battleRepository.findByBattleCode(battleCode);
        todoDataService.saveTodoData(battle, todoDataValue, value, session);

        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);

        redirectAttributes.addFlashAttribute("todoDataList", todoDataList);

        String redirectUrl = "/battle/detail?battleCode=" + battleCode;

        return new RedirectView(redirectUrl);
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

        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);

        redirectAttributes.addFlashAttribute("todoDataList", todoDataList);

        String redirectUrl = "/battle/detail?battleCode=" + battleCode;

        return new RedirectView(redirectUrl);
    }

}
