package pl.atins.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Serhii Manko"
 */

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @GetMapping
    public String showLoginPage(Model model) {
        return "schedule";
    }


}