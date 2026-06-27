package com.example.Myroutine;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password) {

        User user = new User(username, password);
            userRepository.save(user);

            return "redirect:/";
        }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        if (!user.getPassword().equals(password)) {
            return "redirect:/login";
        }

        session.setAttribute("loginUser", user);

        if(user == null) {
            return "redirect:/login";
        }

        if (!user.getPassword().equals(password)){
            return "redirect:/login";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}

