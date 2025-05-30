package com.example.demo.controller.user;

import com.example.demo.InvalidEmailFormatException.InvalidEmailFormatException;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Data
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signup() {
        return "sign/signup";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirect", required = false) String redirect,
                        HttpServletRequest request){
        if (redirect != null) {
            request.getSession().setAttribute("redirectAfterLogin", redirect);
        }
        return "sign/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute UserDto userdto, UserEntity user, Model model) {
        String email = user.getEmail();
        String password = user.getPassword();
        String userPwRe = userdto.getUserPwRe();
        String phone = user.getPhone();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidEmailFormatException("이메일 형식이 올바르지 않습니다.");
        }

        if(password == null || !password.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$")){
            throw new InvalidEmailFormatException("영문 숫자 특수기호 조합 8자리 이상 입력해주세요");
        }
        if(!password.trim().equals(userPwRe.trim())) {
            throw new InvalidEmailFormatException("비밀번호가 일치하지 않습니다");
        }
        if(phone == null || !phone.matches("^01[016789]\\d{8}$")){
            throw  new InvalidEmailFormatException("전화번호는 11자리를 입력해주세요");
        }
        try {
            System.out.println(">>> 회원가입 시도: " + user.getEmail());
            userService.registerUser(user);
            System.out.println(">>> 저장 성공");
            model.addAttribute("success", "회원가입 완료");
            return "sign/login"; // 성공 시 로그인 페이지로
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "sign/signup"; // 에러 시 회원가입 폼으로
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails, HttpSession session, Model model) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername());
        userService.deleteById(user.getUserId());
        session.invalidate();
        model.addAttribute("message", "회원 탈퇴가 완료되었습니다.");
        return "sign/delete-success";
    }

    @PostMapping("/set-role")
    @ResponseBody
    public void setRole(HttpSession session, @RequestParam String role) {
        session.setAttribute("oauth2_role", role);
        System.out.println("UserContorller role: " + role);
    }
}
