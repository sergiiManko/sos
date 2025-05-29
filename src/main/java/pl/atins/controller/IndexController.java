package pl.atins.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.atins.domain.User;

import java.util.stream.Collectors;

/**
 * @author "Serhii Manko"
 */

@Controller
@RequestMapping(value = {"/", "/index"})
public class IndexController {

    @GetMapping
    public String showIndexPage(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            final User user = (User) authentication.getPrincipal();
            model.addAttribute("userName", user.getFullName());
            var listOfRoles = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            model.addAttribute("userRole", listOfRoles);
            return "index";
        }
        return "redirect:/login";
    }


}