package DoBattle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/403")
    public String handle403() {
        return "error"; // error.html 템플릿을 렌더링
    }
}
