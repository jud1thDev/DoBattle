package DoBattle.controller;

import DoBattle.domain.Battle;
import DoBattle.domain.User;
import DoBattle.service.UserService;
import DoBattle.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final BattleService battleService;

    @Autowired
    public UserController(UserService userService, BattleService battleService) {
        this.userService = userService;
        this.battleService = battleService;
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username, @RequestParam String identify, @RequestParam String password) {
        if (!userService.isIdentifyAvailable(identify)) {
            return "redirect:/signup?error=IdentifyNotAvailable";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setIdentify(identify);
        newUser.setPassword(password);

        userService.registerUser(newUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String identify, @RequestParam String password, HttpSession session, Model model) {

        User user = userService.loginUser(identify, password);

        if (user != null) {
            session.setAttribute("currentUser", user);
            model.addAttribute("currentUser", user); // currentUser 정보를 모델에 추가
            return "redirect:/main";
        } else {
            return "redirect:/login?error=InvalidCredentials";
        }
    }


    @GetMapping("/main")
    public String showMainPage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login"; // 없으면 로그인 페이지로 이동
        }

        model.addAttribute("currentUser", currentUser);

        List<Battle> joinedBattles = battleService.getJoinedBattles(currentUser.getIdentify());
        model.addAttribute("joinedBattles", joinedBattles);
        // joinedBattles는 createUser나 joinUser에 로그인된 유저와 동일한 identify가 존재해야함

        return "main";

    }

}