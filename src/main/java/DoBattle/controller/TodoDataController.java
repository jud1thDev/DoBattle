package DoBattle.controller;

import DoBattle.service.TodoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoDataController {

    @Autowired
    private TodoDataService todoService;

    @PostMapping("/battle/saveTodoData")
    public ResponseEntity<String> saveTodoData(@RequestParam String battleCode,
                                               @RequestParam String todoData,
                                               @RequestParam String value) {
        todoService.saveTodoData(battleCode, todoData, value);
        return ResponseEntity.ok("투두데이터 저장 성공");
    }



}

