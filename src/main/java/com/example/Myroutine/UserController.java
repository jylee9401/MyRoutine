package com.example.Myroutine;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);

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

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/login";
        }

        session.setAttribute("loginUser", user);


        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}

