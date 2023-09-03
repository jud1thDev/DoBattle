package DoBattle.controller;

import DoBattle.domain.Battle;
import DoBattle.repository.BattleRepository;
import DoBattle.service.TodoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

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
        // 리다이렉션할 URL을 설정합니다.
        String redirectUrl = "/battle/detail?battleCode=" + battleCode;
        // 리다이렉션할 URL을 RedirectAttributes에 추가합니다.
        redirectAttributes.addAttribute("redirectUrl", redirectUrl);
        // RedirectView를 반환하여 리다이렉션을 수행합니다.
        return new RedirectView(redirectUrl); // 이 URL은 실제로는 없어도 됩니다.
    }

}
