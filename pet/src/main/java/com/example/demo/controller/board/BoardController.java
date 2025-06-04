package com.example.demo.controller.board;

import com.example.demo.domain.entity.BoardEntity;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.BoardRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.BoardService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

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
    public String viewBoard(@PathVariable("id") Long id, Model model,Principal principal) {
        BoardEntity board = boardService.viewBoardContent(id);
        boolean isLoggedIn = principal != null;
        model.addAttribute("board", board);
        model.addAttribute("isLoggedIn", isLoggedIn);
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

    @PostMapping("/board/delete/{id}")
    public String deleteBoard(@PathVariable("id") Long id,
                              RedirectAttributes attr,
                              Principal principal) {
        BoardEntity board = boardService.viewBoardContent(id);
        UserEntity loginUser = userService.findByEmail(principal.getName());
        Long loginUserId = loginUser.getUserId();

        // 권한 체크 (작성자 또는 ADMIN인지 확인)
        if (!board.getUser().getUserId().equals(loginUserId) && !loginUser.getRole().equals("ROLE_ADMIN")) {
            attr.addFlashAttribute("delSuccess", "삭제 권한이 없습니다.");
            return "redirect:/board";  // 권한 없을 시도 리다이렉트
        }

        boardService.deleteById(board.getBoardId());
        attr.addFlashAttribute("delSuccess", "삭제되었습니다");
        return "redirect:/board";
    }
}
