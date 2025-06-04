package com.example.demo.controller.reserve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ReserveController {
    @GetMapping("/reserve")
    public String reserve() {
        return "reserve/reserve";
    }

//   *** 임시 URL 기능 구현시 삭제 필수 !!! ***
    @GetMapping("/reserve/confirm")
    public String reserveSuccess() {
        return "reserve/reserveConfirm";
    }

}
