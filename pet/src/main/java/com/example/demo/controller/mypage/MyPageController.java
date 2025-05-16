package com.example.demo.controller.mypage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/mypage")
public class MyPageController {
    @GetMapping("/owner")
    public String owner() {
        return "mypage/owner";
    }

    @GetMapping("/sitter")
    public String sitter() {
        return "mypage/sitter";
    }
}
