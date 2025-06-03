package pl.atins.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.atins.dto.GradeDTO;

public interface GradeService {
    Page<GradeDTO> getStudentGrades(Long studentId, Pageable pageable);

    GradeDTO getGradeById(Long id);
}
