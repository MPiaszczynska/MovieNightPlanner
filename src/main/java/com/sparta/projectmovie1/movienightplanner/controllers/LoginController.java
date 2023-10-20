package com.sparta.projectmovie1.movienightplanner.controllers;

import com.sparta.projectmovie1.movienightplanner.models.User;
import com.sparta.projectmovie1.movienightplanner.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, @RequestParam(required = false) String successMessage, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }
        model.addAttribute("successMessage", successMessage);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            return "redirect:/index";
        } else {
            return "redirect:/login?error=true";
        }
    }
}

