package com.example.demo.controller.user;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Data
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/signup")
    public String signup() {
        return "sign/signup";
    }

    @GetMapping("/login")
    public String login(){
        return "sign/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @PostMapping("/signup")
    public String registerUser(UserEntity user, Model model) throws Exception {
        try {
            System.out.println(">>> 회원가입 시도: " + user.getEmail());
            userService.registerUser(user);
            System.out.println(">>> 저장 성공");
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에서 정확한 원인 확인
            model.addAttribute("error", "회원가입 중 오류 발생");
            return "sign/signup";
        }
        model.addAttribute("success", "회원가입 완료");
        return "sign/login";
    }

//    @PostMapping("/login")
//    public String loginUser(@RequestParam String email,
//                            @RequestParam String password,
//                            HttpSession session,
//                            Model model) {
//        UserEntity user = userService.findByEmail(email);
//
//        if (user == null || !user.getPassword().equals(password)) {
//            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
//            return "sign/login";
//        }
//        model.addAttribute("successLogin", "로그인 성공");
//        session.setAttribute("loggedInUser", user);
//        return "sign/login-success";
//    }

    @PostMapping("/delete")
    public String deleteUser(HttpSession session, Model model) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("loggedInUser");
        userService.deleteById(loggedInUser.getUserId());
        session.invalidate();
        model.addAttribute("message", "회원 탈퇴가 완료되었습니다.");
        return "delete-success";
    }
}
