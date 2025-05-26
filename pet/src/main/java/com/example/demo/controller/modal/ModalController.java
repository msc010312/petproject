package com.example.demo.controller.modal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModalController {
    @GetMapping("/modal/sitter-detail")
    public String getSitterDetail(Model model) {
        // 필요하면 model에 데이터 추가
        return "modal/reservemodal :: sitterDetail";
    }
}
