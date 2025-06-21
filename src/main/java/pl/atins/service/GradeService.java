package pl.atins.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.atins.domain.Grade;
import pl.atins.dto.GradeDTO;
import pl.atins.dto.GradeFormDTO;

import java.util.Optional;

public interface GradeService {
    Page<GradeDTO> getStudentGrades(Long studentId, Pageable pageable);

    GradeDTO getGradeById(Long id);

    void addGrade(GradeFormDTO gradeFormDTO, Long teacherId);

    Optional<Grade> getExistingGrade(Long subjectId, Long studentId, Long enrollmentId);
}
