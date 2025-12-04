package com.school.main.controller;

import com.school.main.entity.User;
import com.school.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "admin/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String fullName,
            @RequestParam(required = false) String password,
            RedirectAttributes redirectAttributes) {
        try {
            userService.updateProfile(userDetails.getUsername(), fullName, password);
            redirectAttributes.addFlashAttribute("successMessage", "Профилът е обновен успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при обновяване на профила: " + e.getMessage());
        }
        return "redirect:/profile";
    }
}
