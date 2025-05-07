package com.example.demo.controller;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Data

public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/signup")
    public String registerUser(UserEntity user, Model model) throws Exception {
        userService.registerUser(user);
        model.addAttribute("success", "회원가입이 완료되었습니다.");
        return "signup-success";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        UserEntity user = userService.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }

        session.setAttribute("loggedInUser", user);
        return "redirect:/main";
    }
}
