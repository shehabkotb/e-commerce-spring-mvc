package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.RegistrationDto;
import com.vodafone.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("user", registrationDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegistrationDto registrationDto, BindingResult bindingResult, Model model) throws MessagingException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", registrationDto);
            return "register";
        }

        userService.registerUser(registrationDto);
        return "redirect:/login?success";
    }

    @GetMapping("/reset")
    public String getResetForm() {
        return "reset-account";
    }

    @PostMapping("/reset")
    public String resetUser(@ModelAttribute("email") String email, Model model) throws MessagingException {
        userService.resetAccount(email);
        return "redirect:/reset?success";
    }

    @GetMapping("/confirm-account/{token}")
    public String confirmUserAccount(@PathVariable("token") String confirmationToken) {
        userService.confirmEmail(confirmationToken);
        return "verification-success";
    }
}
