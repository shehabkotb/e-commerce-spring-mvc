package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.model.UserEntity;
import com.vodafone.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserEntity user) throws MessagingException {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/confirm-account/{token}")
    public String confirmUserAccount(@PathVariable("token") String confirmationToken) {
        userService.confirmEmail(confirmationToken);
        return "verification-success";
    }
}
