package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.atins.dto.EnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.exception.StudentNotFoundException;
import pl.atins.service.EnrollmentService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enrollments")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public String showEnrollments(Model model) {
        try {
            List<EnrollmentDTO> enrollments = enrollmentService.getStudentEnrollments();
            List<SubjectDTO> availableSubjects = enrollmentService.getAvailableSubjects();

            // Extract IDs of subjects the student is already enrolled in
            List<Long> enrolledSubjectIds = enrollments.stream()
                    .map(e -> e.getSubject().getId())
                    .collect(Collectors.toList());

            model.addAttribute("enrollments", enrollments);
            model.addAttribute("availableSubjects", availableSubjects);
            model.addAttribute("enrolledSubjectIds", enrolledSubjectIds);
            return "enrollment";
        } catch (StudentNotFoundException e) {
            return "redirect:/403";
        }
    }

    @PostMapping("/enroll/{subjectId}")
    public String enrollInSubject(@PathVariable Long subjectId) {
        enrollmentService.enrollInSubject(subjectId);
        return "redirect:/enrollments";
    }

    @PostMapping("/waitlist/{subjectId}")
    public String joinWaitlist(@PathVariable Long subjectId) {
        enrollmentService.joinWaitlist(subjectId);
        return "redirect:/enrollments";
    }

    @PostMapping("/drop/{enrollmentId}")
    public String dropEnrollment(@PathVariable Long enrollmentId) {
        enrollmentService.dropEnrollment(enrollmentId);
        return "redirect:/enrollments";
    }
}
