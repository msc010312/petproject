package com.example.demo.controller.mypage;

import com.example.demo.domain.dto.OwnerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/mypage")
public class MyPageController {
    @GetMapping("/ownerpage")
    public String ownerPage(Model model) {
        OwnerDto user = new OwnerDto("aaa", 2, 3, 1, 150000);
        model.addAttribute("user", user);
        return "mypage/ownerpage";
    }

    @GetMapping("/sitter")
    public String sitter() {
        return "mypage/sitter";
    }
}
