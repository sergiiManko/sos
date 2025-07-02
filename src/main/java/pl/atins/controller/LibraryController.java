package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.atins.dto.LoanDto;
import pl.atins.service.LoanService;
import pl.atins.service.SecurityService;

import java.util.List;

/**
 * @author "Serhii Manko"
 */

@Controller
@RequestMapping("/library")
@PreAuthorize("hasRole('ROLE_STUDENT')")
@RequiredArgsConstructor
public class LibraryController {

    private final LoanService loanService;
    private final SecurityService securityService;

    @GetMapping
    public String showLibrary(Model model) {
        var currentUser = securityService.getCurrentUser();
        List<LoanDto> loans = loanService.getLoansForStudent(currentUser.getId());
        model.addAttribute("loans", loans);
        return "library";
    }
}