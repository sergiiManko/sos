package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.atins.domain.User;
import pl.atins.service.UserService;

import java.util.stream.Collectors;

/**
 * @author Serhii Manko
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            final User user = (User) authentication.getPrincipal();
            model.addAttribute("userName", user.getFullName());
            var listOfRoles = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
            model.addAttribute("userRole", listOfRoles);
        }
        long studentCount = userService.countStudents();
        long teacherCount = userService.countTeachers();

        model.addAttribute("studentCount", studentCount);
        model.addAttribute("teacherCount", teacherCount);

        return "admin_index";
    }
}

