package com.example.Myroutine;

import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }


    @GetMapping("/")
    public String home(@RequestParam(required = false) String status,
                       @RequestParam(required = false) String keyword,
                       Model model,
                       HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("routines", routineService.searchByKeyword(keyword, loginUser));
        } else {
            model.addAttribute("routines", routineService.findByStatus(status, loginUser));
        }
        //model.addAttribute("keyword", keyword);
        if (loginUser == null) {
            model.addAttribute("completionRate", 0);
        } else {
            model.addAttribute("completionRate", routineService.getCompletionRate(loginUser));
        }
        return "index";
    }

    @PostMapping("/add")
    public String addRoutine(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                             LocalDate date,
                             HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        routineService.addRoutine( title, description, date, loginUser);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoutine(@PathVariable Long id,
                                HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        if (!routineService.isOwner(id, loginUser)) {
            return "redirect:/";
        }

        routineService.deleteRoutine(id);
        return "redirect:/";
    }

    @PostMapping("/toggle/{id:\\d+}")
    public String toggleRoutine(@PathVariable Long id,
                                HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        if (!routineService.isOwner(id, loginUser)) {
            return "redirect:/";
        }

        routineService.toggleRoutine(id);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           Model model,
                           HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        if (!routineService.isOwner(id, loginUser)) {
            return "redirect:/";
        }

        Routine routine = routineService.findRoutine(id);

        model.addAttribute("routine", routine);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateRoutine(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String description,
                                @RequestParam
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                LocalDate date) {
        routineService.updateRoutine(id, title, description, date);
        return "redirect:/";
    }

}