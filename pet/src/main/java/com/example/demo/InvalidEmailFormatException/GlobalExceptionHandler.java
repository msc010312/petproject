package com.example.demo.InvalidEmailFormatException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailFormatException.class)
    public String handleInvalidEmail(InvalidEmailFormatException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "sign/signup";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("error", "서버 오류: " + ex.getMessage());
        return "sign/signup";
    }
}