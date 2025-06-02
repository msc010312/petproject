package com.example.demo.controller.board;

import com.example.demo.domain.entity.BoardEntity;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/board")
    public String board(Model model, Principal principal, @RequestParam(value="page", defaultValue="0") int page){
        List<BoardEntity> boardList = boardService.getAllBoard(page);
        boolean isLoggedIn = principal != null;

        System.out.println("boardlist : "+boardList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "board/board";
    }

    @GetMapping("/board/add")
    public String boardAdd() {
        return "board/addContent";
    }

    @GetMapping("/board/view/{id}")
    public String viewBoard(@PathVariable("id") Long id, Model model) {
        BoardEntity board = boardService.viewBoardContent(id);
        model.addAttribute("board", board);
        return "board/viewContent";
    }

    @PostMapping("/board/add")
    public String saveBoard(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            Principal principal) {
        String email = principal.getName();
        UserEntity user = userRepository.findByEmail(email);
        BoardEntity board = BoardEntity.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
        System.out.println(user);
        boardService.saveContent(board);
        return "redirect:/board";
    }
}
