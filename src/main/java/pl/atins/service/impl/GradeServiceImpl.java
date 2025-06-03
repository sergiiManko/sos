package pl.atins.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.atins.domain.Grade;
import pl.atins.domain.Student;
import pl.atins.dto.GradeDTO;
import pl.atins.repository.GradeRepository;
import pl.atins.service.GradeService;
import pl.atins.service.StudentService;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;

    @Override
    public Page<GradeDTO> getStudentGrades(Long studentId, Pageable pageable) {
        Student student = studentService.findById(studentId);
        Page<Grade> grades = gradeRepository.findByTranscriptStudent(student, pageable);
        return grades.map(GradeDTO::fromEntity);
    }

    @Override
    public GradeDTO getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Grade not found with ID: " + id));

        return GradeDTO.fromEntity(grade);
    }
}
