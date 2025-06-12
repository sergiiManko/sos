package pl.atins.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.atins.domain.Teacher;
import pl.atins.dto.GradeFormDTO;
import pl.atins.dto.StudentEnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.service.GradeService;
import pl.atins.service.TeacherService;

import java.util.List;

@Controller
@RequestMapping("/teacher/grades")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_TEACHER')")
public class TeacherGradeController {

    private final TeacherService teacherService;
    private final GradeService gradeService;

    @GetMapping("/add")
    public String showAddGradeForm(
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("studentId") Long studentId,
            @RequestParam("enrollmentId") Long enrollmentId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        List<SubjectDTO> subjects = teacherService.getSubjectDTOS(subjectId, userDetails);
        if (subjects == null) return "redirect:/teacher/subjects";

        GradeFormDTO gradeFormDTO = new GradeFormDTO();
        gradeFormDTO.setSubjectId(subjectId);
        gradeFormDTO.setStudentId(studentId);
        gradeFormDTO.setEnrollmentId(enrollmentId);

        SubjectDTO subject = subjects.stream()
                .filter(s -> s.getId().equals(subjectId))
                .findFirst()
                .orElse(null);

        List<StudentEnrollmentDTO> enrolledStudents = teacherService.getEnrolledStudents(subjectId);
        StudentEnrollmentDTO student = enrolledStudents.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);

        model.addAttribute("gradeForm", gradeFormDTO);
        model.addAttribute("subject", subject);
        model.addAttribute("student", student);

        return "teacher/add-grade";
    }


    @PostMapping("/add")
    public String addGrade(
            @Valid @ModelAttribute("gradeForm") GradeFormDTO gradeFormDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            Teacher teacher = teacherService.findTeacherByEmail(userDetails.getUsername());
            List<SubjectDTO> subjects = teacherService.getTeacherSubjects(teacher.getId());

            SubjectDTO subject = subjects.stream()
                    .filter(s -> s.getId().equals(gradeFormDTO.getSubjectId()))
                    .findFirst()
                    .orElse(null);

            List<StudentEnrollmentDTO> enrolledStudents = teacherService.getEnrolledStudents(gradeFormDTO.getSubjectId());
            StudentEnrollmentDTO student = enrolledStudents.stream()
                    .filter(s -> s.getStudentId().equals(gradeFormDTO.getStudentId()))
                    .findFirst()
                    .orElse(null);

            model.addAttribute("subject", subject);
            model.addAttribute("student", student);

            return "teacher/add-grade";
        }

        Teacher teacher = teacherService.findTeacherByEmail(userDetails.getUsername());
        gradeService.addGrade(gradeFormDTO, teacher.getId());

        redirectAttributes.addFlashAttribute("successMessage",
                "Grade successfully added for " + gradeFormDTO.getScore());

        return "redirect:/teacher/subjects/" + gradeFormDTO.getSubjectId() + "/students";
    }
}
