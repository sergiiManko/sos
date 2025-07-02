package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.atins.domain.Student;
import pl.atins.domain.User;
import pl.atins.service.LoanService;

import java.util.stream.Collectors;

/**
 * @author "Serhii Manko"
 */

@Controller
@RequestMapping(value = {"/", "/index"})
@RequiredArgsConstructor
public class IndexController {
    private final LoanService loanService;

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
            if (user instanceof Student student) {
                model.addAttribute("loansCount", loanService.countLoansByStatus(student.getId(), "in use"));
            }
            return "index";
        }
        return "redirect:/login";
    }


}