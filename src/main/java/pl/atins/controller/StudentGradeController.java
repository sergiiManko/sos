package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.atins.domain.Student;
import pl.atins.dto.GradeDTO;
import pl.atins.service.GradeService;
import pl.atins.service.SecurityService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/student/grades")
@RequiredArgsConstructor
public class StudentGradeController {

    private final GradeService gradeService;
    private final SecurityService securityService;

    @GetMapping
    public String viewGrades(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {

            PageRequest pageRequest = PageRequest.of(page, size);
            Page<GradeDTO> gradePage = gradeService.getStudentGrades(student.getId(), pageRequest);

            model.addAttribute("grades", gradePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", gradePage.getTotalPages());
            model.addAttribute("totalItems", gradePage.getTotalElements());

            if (gradePage.getTotalPages() > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(0, gradePage.getTotalPages() - 1)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            return "grade";
        }
        return "redirect:/login";

    }

    @GetMapping("/{id}")
    public String viewGradeDetails(
            @PathVariable("id") Long gradeId,
            Model model) {
        GradeDTO grade = gradeService.getGradeById(gradeId);
        model.addAttribute("grade", grade);

        return "grade-detail";
    }
}
