package pl.atins.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author "Serhii Manko"
 */

@Controller
public class IndexController {

    @GetMapping("/")
    public String showLoginPage(Model model) {
        return "index";
    }


}