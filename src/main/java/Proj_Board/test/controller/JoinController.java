package Proj_Board.test.controller;

import Proj_Board.test.model.User;
import Proj_Board.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class JoinController {
    private final UserService userService;

    @Autowired
    public JoinController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 화면
    @GetMapping
    public String getJoin(){
        return "signup";
    }

    // 회원가입
    @PostMapping
    public String postJoin(@RequestParam String userid, @RequestParam String password){
        User user = new User();
        user.setUserId(userid);
        user.setUserPw(password);
        userService.createUser(user);

        return "redirect:/login";
    }

}
