package Proj_Board.test.controller;

import Proj_Board.test.model.User;
import Proj_Board.test.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLogin(){
        return "login";
    }

    @PostMapping
    public String postLogin(@RequestParam String userid, @RequestParam String password, HttpServletRequest request, Model model){
        User user = userService.findByUser(userid, password);
        if(user != null){
            request.getSession().invalidate();
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getUserId());
            session.setMaxInactiveInterval(180);
            return "redirect:/board";
        } else{
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}

