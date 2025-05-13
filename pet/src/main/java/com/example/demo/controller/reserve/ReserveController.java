package com.example.demo.controller.reserve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ReserveController {
    @GetMapping("/reserve")
    public String reserve() {
        return "reserve";
    }
}
