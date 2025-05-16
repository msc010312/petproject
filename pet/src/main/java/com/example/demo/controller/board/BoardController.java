package com.example.demo.controller.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class BoardController {
    @GetMapping("/board")
    public String board(){
        return "board/board";
    }
}
