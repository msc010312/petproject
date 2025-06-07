package com.example.demo.controller.mypage;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/ownerpage")
    public String ownerPage() {
        return "mypage/ownerpage";
    }

    @GetMapping("/ownerprofile")
    public String ownerProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findByEmail(email);

        model.addAttribute("user", user);
        return "mypage/ownerprofile";
    }

}
