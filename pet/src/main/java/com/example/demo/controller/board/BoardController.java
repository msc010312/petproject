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

    @GetMapping("/board/add")
    public String boardAdd() {
        return "board/addContent";
    }

    @GetMapping("/board/view")
    public String boardView() {
        return "board/viewContent";
    }
}
