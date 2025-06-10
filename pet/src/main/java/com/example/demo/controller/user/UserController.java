package com.example.demo.controller.user;

import com.example.demo.InvalidEmailFormatException.InvalidEmailFormatException;
import com.example.demo.domain.dto.OwnerForm;
import com.example.demo.domain.dto.SitterForm;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.OwnerRepository;
import com.example.demo.domain.repository.SitterRepository;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Data
@Slf4j

public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SitterRepository sitterRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Value("${upload.path}")  // 서버에 파일이 저장될 경로 (application.properties에서 설정)
    private String uploadPath;


    @GetMapping("/signup")
    public String signup() {
        return "sign/signup";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "redirect", required = false) String redirect,
                        HttpServletRequest request,HttpSession session, Model model){
        String errMsg = (String) session.getAttribute("loginErrorMessage");
        if (errMsg != null) {
            model.addAttribute("loginErrorMessage", errMsg);
            session.removeAttribute("loginErrorMessage");
        }


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
        System.out.println("UserController role: " + role);
    }

    @PostMapping("/update/owner/{userId}")
    public String updateOwner(@PathVariable Long userId, @ModelAttribute OwnerForm ownerForm, @RequestParam("profileImage") MultipartFile imageFile) throws IOException {

        UserEntity userEntity = userService.findById(userId);

        userEntity.setName(ownerForm.getName());
        userEntity.setAddress(ownerForm.getAddress());
        userEntity.setPhone(ownerForm.getPhone());

        // 이미지 파일이 존재하면 처리
        if (!imageFile.isEmpty()) {
            String filename = imageFile.getOriginalFilename();
            String filePath = "/uploads/" + filename; // 서버에 저장될 파일 경로

            // 디렉토리 존재 여부 확인 및 생성
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리 생성
            }

            // 파일을 서버에 저장
            File dest = new File(uploadPath + "/" + filename);
            imageFile.transferTo(dest);  // 파일 저장

            // 경로를 데이터베이스에 저장
            userEntity.setProfileImageUrl(filePath); // 경로를 데이터베이스에 저장
        }

        userService.saveUser(userEntity);

        return "redirect:/mypage/ownerpage";
    }

    @PostMapping("/update/sitter/{userId}")
    public String updateSitter(@PathVariable Long userId, @ModelAttribute SitterForm sitterForm, @RequestParam("profileImage") MultipartFile imageFile) throws IOException {

        UserEntity userEntity = userService.findById(userId);
        SitterEntity sitterEntity = sitterRepository.findByUser(userEntity);

        userEntity.setName(sitterForm.getName());
        userEntity.setAddress(sitterForm.getAddress());
        userEntity.setPhone(sitterForm.getPhone());

        sitterEntity.setWalkPrice(sitterForm.getWalkPrice() != null ? sitterForm.getWalkPrice() : 0L);
        sitterEntity.setHotelPrice(sitterForm.getHotelPrice() != null ? sitterForm.getHotelPrice() : 0L);
        sitterEntity.setDayPrice(sitterForm.getDayPrice() != null ? sitterForm.getDayPrice() : 0L);
        sitterEntity.setPresentation(
                sitterForm.getPresentation() != null ? sitterForm.getPresentation() : ""
        );

        // 이미지 파일이 존재하면 처리
        if (!imageFile.isEmpty()) {
            String filename = imageFile.getOriginalFilename();
            String filePath = "/uploads/" + filename; // 서버에 저장될 파일 경로

            // 디렉토리 존재 여부 확인 및 생성
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리 생성
            }

            // 파일을 서버에 저장
            File dest = new File(uploadPath + "/" + filename);
            imageFile.transferTo(dest);  // 파일 저장

            // 경로를 데이터베이스에 저장
            userEntity.setProfileImageUrl(filePath); // 경로를 데이터베이스에 저장
        }

        userService.saveUser(userEntity);
        sitterRepository.save(sitterEntity);

        return "redirect:/mypage/sitterpage";
    }
}
