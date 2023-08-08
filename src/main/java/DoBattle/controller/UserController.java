package DoBattle.controller;

import DoBattle.domain.User;
import DoBattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username, @RequestParam String identify, @RequestParam String password) {
        if (!userService.isUsernameAvailable(username)) {
            return "redirect:/signup?error=UsernameNotAvailable";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setIdentify(identify);
        newUser.setPassword(password); // 텍스트로 받는 거라 보안상 위험 있음

        userService.registerUser(newUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}