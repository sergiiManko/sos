package pl.atins.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Grade;
import pl.atins.domain.Student;
import pl.atins.domain.Subject;
import pl.atins.domain.Teacher;
import pl.atins.dto.StudentEnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.repository.EnrollmentRepository;
import pl.atins.repository.SubjectRepository;
import pl.atins.repository.TeacherRepository;
import pl.atins.service.TeacherService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with email: " + email));
    }

    @Override
    public List<SubjectDTO> getTeacherSubjects(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with ID: " + teacherId));

        List<Subject> subjects = subjectRepository.findByTeacher(teacher);
        return subjects.stream()
                .map(SubjectDTO::fromEntity)
                .toList();
    }

    @Override
    public List<StudentEnrollmentDTO> getEnrolledStudents(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NoSuchElementException("Subject not found with ID: " + subjectId));

        List<Enrollment> enrollments = enrollmentRepository.findBySubjectAndStatus(subject, Enrollment.STATUS_ENROLLED);

        return enrollments.stream()
                .map(enrollment -> {
                    Student student = enrollment.getStudent();
                    List<Grade> grades = student.getTranscript().getGrades();
                    boolean hasGrade = grades.stream()
                            .anyMatch(grade -> grade.getEnrollment().getSubject().equals(subject));
                    return StudentEnrollmentDTO.fromEntities(enrollment, student, hasGrade);
                })
                .toList();
    }

    public List<SubjectDTO> getSubjectDTOS(Long subjectId, UserDetails userDetails) {
        Teacher teacher = findTeacherByEmail(userDetails.getUsername());
        List<SubjectDTO> subjects = getTeacherSubjects(teacher.getId());

        boolean hasAccess = subjects.stream().anyMatch(s -> s.getId().equals(subjectId));
        if (!hasAccess) {
            return Collections.emptyList();
        }
        return subjects;
    }
}
