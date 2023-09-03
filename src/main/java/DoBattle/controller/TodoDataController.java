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

    @PostMapping("/battle/{battleCode}/saveTodoData")
    public ResponseEntity<?> saveTodoData(@PathVariable String battleCode,
                                          @RequestParam String todoDataValue,
                                          @RequestParam String value,
                                          HttpSession session) {
        Battle battle = battleRepository.findByBattleCode(battleCode);

        if (battle != null) {
            todoDataService.saveTodoData(battle, todoDataValue, value, session);
            String redirectUrl = "/battle/detail?battleCode=" + battleCode;

            return ResponseEntity.ok().body("{\"redirectUrl\":\"" + redirectUrl + "\"}");
        } else {
            return ResponseEntity.badRequest().body("올바른 배틀 코드를 입력하세요.");
        }
    }

}
