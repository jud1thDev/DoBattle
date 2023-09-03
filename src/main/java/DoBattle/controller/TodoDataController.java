package DoBattle.controller;

import DoBattle.domain.Battle;
import DoBattle.domain.TodoData;
import DoBattle.repository.BattleRepository;
import DoBattle.service.TodoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class TodoDataController {

    @Autowired
    private TodoDataService todoDataService;

    @Autowired
    private BattleRepository battleRepository;

    @PostMapping("/battle/{battleCode}/saveTodoData") // 경로 변수로 battleCode 받음
    public RedirectView saveTodoData(@PathVariable String battleCode,
                                     @RequestParam String todoDataValue,
                                     @RequestParam String value,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session) {

        Battle battle = battleRepository.findByBattleCode(battleCode);
        todoDataService.saveTodoData(battle, todoDataValue, value, session);

        // 데이터베이스에서 todoData 관련 값을 불러옵니다.
        List<TodoData> todoDataList = todoDataService.getTodoDataByBattle(battle);

        // 리다이렉트 페이지로 todoDataList를 전달합니다.
        redirectAttributes.addFlashAttribute("todoDataList", todoDataList);

        // 리다이렉트 URL 설정
        String redirectUrl = "/battle/detail?battleCode=" + battleCode;

        return new RedirectView(redirectUrl);
    }


}
