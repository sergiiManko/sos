package pl.atins.service;

import org.springframework.security.core.userdetails.UserDetails;
import pl.atins.domain.Teacher;
import pl.atins.dto.StudentEnrollmentDTO;
import pl.atins.dto.SubjectDTO;

import java.util.List;

public interface TeacherService {
    Teacher findTeacherByEmail(String email);

    List<SubjectDTO> getTeacherSubjects(Long teacherId);

    List<StudentEnrollmentDTO> getEnrolledStudents(Long subjectId);

    List<SubjectDTO> getSubjectDTOS(Long subjectId, UserDetails userDetails);
}
