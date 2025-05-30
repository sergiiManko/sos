package pl.atins.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Student;
import pl.atins.domain.Subject;
import pl.atins.dto.EnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.repository.EnrollmentRepository;
import pl.atins.repository.SubjectRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final SubjectRepository subjectRepository;
    private final SecurityService securityService;

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getStudentEnrollments() {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {
            List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

            return enrollments.stream()
                    .map(EnrollmentDTO::fromEntity)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> getAvailableSubjects() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(SubjectDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void enrollInSubject(Long subjectId) {
        Optional.ofNullable(securityService.getCurrentUser())
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .flatMap(student -> subjectRepository.findById(subjectId)
                        .map(subject ->
                                enrollmentRepository.findByStudentAndSubject(student, subject)
                                        .orElseGet(() -> {
                                            Enrollment e = new Enrollment();
                                            e.setStudent(student);
                                            e.setSubject(subject);
                                            return e;
                                        })
                        )
                        .map(enrollment -> {
                            enrollment.setStatus(Enrollment.STATUS_ENROLLED);
                            enrollment.setEnrollmentDate(LocalDate.now());
                            return enrollment;
                        })).ifPresent(enrollmentRepository::save);
    }


    @Transactional
    public void joinWaitlist(Long subjectId) {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {
            Subject subject = subjectRepository.findById(subjectId).orElseThrow();
            Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndSubject(student, subject);
            existingEnrollment.ifPresentOrElse((enrollment) -> {
                if (Enrollment.STATUS_DROPPED.equals(enrollment.getStatus())) {
                    enrollment.setStatus(Enrollment.STATUS_WAITLISTED);
                    enrollment.setEnrollmentDate(LocalDate.now());
                    enrollmentRepository.save(enrollment);
                }
            }, () -> {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setSubject(subject);
                enrollment.setStatus(Enrollment.STATUS_WAITLISTED);
                enrollment.setEnrollmentDate(LocalDate.now());
                enrollmentRepository.save(enrollment);
            });
        }
    }

    @Transactional
    public void dropEnrollment(Long enrollmentId) {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {
            Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
            if (enrollment.getStudent().getId().equals(student.getId())) {
                enrollment.setStatus(Enrollment.STATUS_DROPPED);
                enrollmentRepository.save(enrollment);
            }
        }
    }
}
