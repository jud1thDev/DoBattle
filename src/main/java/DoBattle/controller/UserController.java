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
        return "signup";
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
        return "login";
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
        return "main";
    }

    @PostMapping("/makeBattleFromUser")
    public String makeBattleFromUser(@RequestParam String battleName, @RequestParam String battleCategory, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        String battleCode = battleService.generateUniqueBattleCode();

        Battle newBattle = new Battle();
        newBattle.setBattleName(battleName);
        newBattle.setBattleCategory(battleCategory);
        newBattle.setCode(battleCode);
        newBattle.setCreator(currentUser);

        battleService.makeBattle(newBattle);

        return "redirect:/makeBattleSuccess?battleCode=" + battleCode;
    }
}