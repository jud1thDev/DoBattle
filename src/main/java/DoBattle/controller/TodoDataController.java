package DoBattle.controller;

import DoBattle.service.TodoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class TodoDataController {

    @Autowired
    private TodoDataService todoService;

    @PostMapping("/battle/{battleCode}/saveTodoData")
    public RedirectView saveTodoData(@PathVariable String battleCode,
                                     @RequestParam String todoDataValue,
                                     @RequestParam String value,
                                     RedirectAttributes redirectAttributes) {
        todoService.saveTodoData(battleCode, todoDataValue, value);
        // 리다이렉션할 URL을 설정합니다.
        String redirectUrl = "/battle/detail?battleCode=" + battleCode;
        // 리다이렉션할 URL을 RedirectAttributes에 추가합니다.
        redirectAttributes.addAttribute("redirectUrl", redirectUrl);
        // RedirectView를 반환하여 리다이렉션을 수행합니다.
        return new RedirectView("/redirect"); // 이 URL은 실제로는 없어도 됩니다.
    }



}
