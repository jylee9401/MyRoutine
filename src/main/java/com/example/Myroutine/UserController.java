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

            return "redirect:/login";
        }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        System.out.println("로그인 시도 username = " + username);
        
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("유저없음");
            return "redirect:/login";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("비밀번호 불일치");
            return "redirect:/login";
        }
        System.out.println("로그인 성공");
        session.setAttribute("loginUser", user);


        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}

