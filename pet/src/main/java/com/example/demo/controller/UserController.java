package com.example.demo.controller;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.service.UserServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Data

public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/signup")
    public String registerUser(UserEntity user, Model model) throws Exception {
        userService.registerUser(user);
        model.addAttribute("success", "회원가입이 완료되었습니다.");
        return "signup-success";
    }

}
