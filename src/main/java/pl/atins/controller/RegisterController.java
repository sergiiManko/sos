package pl.atins.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.atins.domain.Department;
import pl.atins.dto.StudentRegistrationDTO;
import pl.atins.repository.DepartmentRepository;
import pl.atins.service.StudentRegistrationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
@Validated
public class RegisterController {

    private final StudentRegistrationService studentRegistrationService;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("student")) {
            model.addAttribute("student", new StudentRegistrationDTO());
        }
        populateModelForUI(model);

        return "register";
    }

    private void populateModelForUI(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        model.addAttribute("studyModes", List.of("Full-time", "Part-time", "Evening", "Weekend", "Online"));
        model.addAttribute("gradesTitle", List.of("Bachelor", "Master", "PhD"));
    }

    @PostMapping
    public String registerStudent(
            @Valid @ModelAttribute("student") StudentRegistrationDTO studentDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            populateModelForUI(model);

            return "register";
        }

        try {
            studentRegistrationService.register(studentDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now log in.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            populateModelForUI(model);
            return "register";
        }
    }
}