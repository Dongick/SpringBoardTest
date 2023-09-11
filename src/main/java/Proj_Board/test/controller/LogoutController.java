package Proj_Board.test.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    // 로그아웃
    @GetMapping
    public String getLogout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
