package com.example.demo.controller.mypage;

import com.example.demo.domain.entity.*;
import com.example.demo.domain.repository.OwnerRepository;
import com.example.demo.domain.repository.PetRepository;
import com.example.demo.domain.repository.ReserveRepository;
import com.example.demo.domain.repository.SitterRepository;
import com.example.demo.service.ReservationService;
import com.example.demo.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private SitterRepository sitterRepository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/ownerpage")
    public String ownerPage(@AuthenticationPrincipal UserDetails userDetails, Model model,
                            @RequestParam(value="page", defaultValue = "0")int page) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findByEmail(email);
        OwnerEntity owner = ownerRepository.findByUser(user);
        SitterEntity sitter = sitterRepository.findByUser(user);
        List<ReserveEntity> sitterReserves = reserveRepository.findBySitter(sitter);
        List<ReserveEntity> reservations = reserveRepository.findByOwner(owner);

        // 진행 중인 예약만 필터링 (예약 확정 상태)
        List<ReserveEntity> ongoingReservations = reservations.stream()
                .filter(reserve -> "예약 확정".equals(reserve.getStatus()))
                .collect(Collectors.toList());

        // 완료된 예약만 필터링 (완료 상태)
        List<ReserveEntity> completedReservations = reservations.stream()
                .filter(reserve -> "완료".equals(reserve.getStatus()))
                .collect(Collectors.toList());

        List<ReserveEntity> reserveList = reservationService.getAllReserve(page);
        int totalCount = reservationService.getTotalCount();
        int totalPages = (int) Math.ceil((double)totalCount / 2);

        model.addAttribute("owner", owner);
        model.addAttribute("sitter", sitter);
        model.addAttribute("reservations", reservations);
        model.addAttribute("ongoingReservations", ongoingReservations); // 진행중인 예약
        model.addAttribute("completedReservations", completedReservations); // 완료된 예약
        model.addAttribute("sitterReservations", sitterReserves);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("reserveList", reserveList);
        return "mypage/ownerpage";
    }

    @GetMapping("/ownerprofile")
    public String ownerProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findByEmail(email);

        model.addAttribute("user", user);
        return "mypage/ownerprofile";
    }

    @GetMapping("/sitterpage")
    public String sitterPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findByEmail(email);
        SitterEntity sitter = sitterRepository.findByUser(user);
        OwnerEntity owner = ownerRepository.findByUser(user);
        List<PetEntity> pet = petRepository.findByOwner(owner);
        List<ReserveEntity> reservations = reserveRepository.findBySitter(sitter);
        model.addAttribute("sitter", sitter);
        model.addAttribute("reservations", reservations);
        model.addAttribute("pet",pet);
        return "mypage/sitterpage";
    }

    @GetMapping("/sitterprofile")
    public String sitterProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findByEmail(email);
        SitterEntity sitter = sitterRepository.findByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("sitter", sitter);

        log.info("시터 정보: {}", sitter);

        return "mypage/sitterprofile";
    }

    @GetMapping("")
    public String redirectMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        UserEntity user = userService.findByEmail(email);

        OwnerEntity owner = ownerRepository.findByUser(user);
        if (owner != null) {
            return "redirect:/mypage/ownerpage";
        }

        SitterEntity sitter = sitterRepository.findByUser(user);
        if (sitter != null) {
            return "redirect:/mypage/sitterpage";
        }

        return "redirect:/";
    }

}
