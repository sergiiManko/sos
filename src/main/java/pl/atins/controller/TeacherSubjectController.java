package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.atins.domain.Teacher;
import pl.atins.dto.StudentEnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.service.TeacherService;

import java.util.List;

@Controller
@RequestMapping("/teacher/subjects")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class TeacherSubjectController {

    private final TeacherService teacherService;

    @GetMapping
    public String listTeacherSubjects(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Teacher teacher = teacherService.findTeacherByEmail(userDetails.getUsername());
        List<SubjectDTO> subjects = teacherService.getTeacherSubjects(teacher.getId());

        model.addAttribute("subjects", subjects);
        model.addAttribute("teacher", teacher);

        return "teacher/subjects";
    }

    @GetMapping("/{subjectId}/students")
    public String listEnrolledStudents(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("subjectId") Long subjectId,
            Model model) {

        Teacher teacher = teacherService.findTeacherByEmail(userDetails.getUsername());
        List<SubjectDTO> subjects = teacherService.getTeacherSubjects(teacher.getId());

        boolean hasAccess = subjects.stream().anyMatch(s -> s.getId().equals(subjectId));
        if (!hasAccess) {
            return "redirect:/teacher/subjects";
        }

        List<StudentEnrollmentDTO> enrolledStudents = teacherService.getEnrolledStudents(subjectId);
        SubjectDTO currentSubject = subjects.stream()
                .filter(s -> s.getId().equals(subjectId))
                .findFirst()
                .orElse(null);

        model.addAttribute("enrolledStudents", enrolledStudents);
        model.addAttribute("subject", currentSubject);
        model.addAttribute("teacher", teacher);

        return "teacher/enrolled-students";
    }
}
