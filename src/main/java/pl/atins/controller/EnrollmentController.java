package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.atins.dto.EnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.exception.EnrollmentCapacityExceededException;
import pl.atins.exception.EnrollmentException;
import pl.atins.exception.StudentNotFoundException;
import pl.atins.exception.SubjectNotFoundException;
import pl.atins.service.EnrollmentService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

            // Extract IDs of subjects the student is already actively enrolled in or waitlisted
            List<Long> activeEnrolledSubjectIds = enrollments.stream()
                    .filter(e -> !"DROPPED".equals(e.getStatus()))
                    .map(e -> e.getSubject().getId())
                    .collect(Collectors.toList());

            // Also include all enrollment IDs for reference
            List<Long> enrolledSubjectIds = enrollments.stream()
                    .map(e -> e.getSubject().getId())
                    .collect(Collectors.toList());

            model.addAttribute("enrollments", enrollments);
            model.addAttribute("availableSubjects", availableSubjects);
            model.addAttribute("enrolledSubjectIds", enrolledSubjectIds);
            model.addAttribute("activeEnrolledSubjectIds", activeEnrolledSubjectIds);
            return "enrollment";
        } catch (StudentNotFoundException e) {
            log.error("Failed to show enrollments", e);
            return "redirect:/403";
        }
    }

    @PostMapping("/enroll/{subjectId}")
    public String enrollInSubject(@PathVariable Long subjectId, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.enrollInSubject(subjectId);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully enrolled in subject");
        } catch (EnrollmentCapacityExceededException e) {
            log.warn("Enrollment capacity exceeded for subject ID: {}", subjectId, e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Subject has reached maximum capacity. Consider joining the waitlist.");
        } catch (EnrollmentException e) {
            log.error("Failed to enroll in subject ID: {}", subjectId, e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/enrollments";
    }

    @PostMapping("/waitlist/{subjectId}")
    public String joinWaitlist(@PathVariable Long subjectId, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.joinWaitlist(subjectId);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully added to waitlist");
        } catch (EnrollmentException e) {
            log.error("Failed to join waitlist for subject ID: {}", subjectId, e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/enrollments";
    }

    @PostMapping("/drop/{enrollmentId}")
    public String dropEnrollment(@PathVariable Long enrollmentId, RedirectAttributes redirectAttributes) {
        try {
            enrollmentService.dropEnrollment(enrollmentId);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully dropped enrollment");
        } catch (Exception e) {
            log.error("Failed to drop enrollment ID: {}", enrollmentId, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to drop enrollment: " + e.getMessage());
        }
        return "redirect:/enrollments";
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public String handleSubjectNotFoundException(SubjectNotFoundException e, RedirectAttributes redirectAttributes) {
        log.error("Subject not found", e);
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/enrollments";
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public String handleStudentNotFoundException(StudentNotFoundException e) {
        log.error("Student not found", e);
        return "redirect:/403";
    }
}
