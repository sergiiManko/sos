package pl.atins.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.Enrollment;
import pl.atins.domain.Student;
import pl.atins.domain.Subject;
import pl.atins.dto.EnrollmentDTO;
import pl.atins.dto.SubjectDTO;
import pl.atins.exception.EnrollmentCapacityExceededException;
import pl.atins.exception.StudentNotFoundException;
import pl.atins.exception.SubjectNotFoundException;
import pl.atins.repository.EnrollmentRepository;
import pl.atins.repository.SubjectRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    @Value("${app.enrollment.default-capacity:30}")
    private int defaultSubjectCapacity;

    private final EnrollmentRepository enrollmentRepository;
    private final SubjectRepository subjectRepository;
    private final SecurityService securityService;

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getStudentEnrollments() {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {
            logger.debug("Fetching enrollments for student ID: {}", student.getId());
            List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
            return EnrollmentDTO.fromEntities(enrollments);
        }
        logger.warn("Attempted to get student enrollments but current user is not a student");
        throw new StudentNotFoundException("Current user is not a student");
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> getAvailableSubjects() {
        logger.debug("Fetching all available subjects");
        List<Subject> subjects = subjectRepository.findAll();
        return SubjectDTO.fromEntities(subjects);
    }


    @Transactional(readOnly = true)
    public boolean hasAvailableCapacity(Subject subject) {
        long enrolledCount = enrollmentRepository.countBySubjectIdAndStatus(subject.getId(), Enrollment.STATUS_ENROLLED);
        return enrolledCount < defaultSubjectCapacity;
    }

    @Transactional
    public void enrollInSubject(Long subjectId) {
        logger.debug("Attempting to enroll student in subject ID: {}", subjectId);
        var currentUser = securityService.getCurrentUser();
        if (!(currentUser instanceof Student student)) {
            logger.warn("Attempted to enroll but current user is not a student");
            throw new StudentNotFoundException("Current user is not a student");
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> {
                    logger.error("Subject not found with ID: {}", subjectId);
                    return new SubjectNotFoundException("Subject not found with ID: " + subjectId);
                });

        // Check capacity before enrolling
        if (!hasAvailableCapacity(subject)) {
            logger.warn("Subject ID: {} has reached maximum enrollment capacity", subjectId);
            throw new EnrollmentCapacityExceededException("Subject has reached maximum enrollment capacity");
        }

        // Check for existing enrollment using the correct method
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndSubject(student, subject);

        if (existingEnrollment.isPresent()) {
            Enrollment enrollment = existingEnrollment.get();
            logger.info("Updating existing enrollment for student ID: {} and subject ID: {}",
                    student.getId(), subject.getId());
            enrollment.setStatus(Enrollment.STATUS_ENROLLED);
            enrollment.setEnrollmentDate(LocalDate.now());
            enrollmentRepository.save(enrollment);
        } else {
            logger.info("Creating new enrollment for student ID: {} and subject ID: {}",
                    student.getId(), subject.getId());
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setSubject(subject);
            enrollment.setStatus(Enrollment.STATUS_ENROLLED);
            enrollment.setEnrollmentDate(LocalDate.now());

            subject.addEnrollment(enrollment);

            enrollmentRepository.save(enrollment);
        }
    }

    @Transactional
    public void joinWaitlist(Long subjectId) {
        logger.debug("Attempting to add student to waitlist for subject ID: {}", subjectId);
        var currentUser = securityService.getCurrentUser();
        if (!(currentUser instanceof Student student)) {
            logger.warn("Attempted to join waitlist but current user is not a student");
            throw new StudentNotFoundException("Current user is not a student");
        }

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> {
                    logger.error("Subject not found with ID: {}", subjectId);
                    return new SubjectNotFoundException("Subject not found with ID: " + subjectId);
                });

        // Check for existing enrollment using the correct method
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndSubject(student, subject);

        if (existingEnrollment.isPresent()) {
            Enrollment enrollment = existingEnrollment.get();
            if (Enrollment.STATUS_DROPPED.equals(enrollment.getStatus())) {
                logger.info("Updating existing enrollment to waitlist for student ID: {} and subject ID: {}",
                        student.getId(), subject.getId());
                enrollment.setStatus(Enrollment.STATUS_WAITLISTED);
                enrollment.setEnrollmentDate(LocalDate.now());
                enrollmentRepository.save(enrollment);
            } else {
                logger.info("Enrollment already exists with status {} for student ID: {} and subject ID: {}",
                        enrollment.getStatus(), student.getId(), subject.getId());
            }
        } else {
            logger.info("Creating new waitlist enrollment for student ID: {} and subject ID: {}",
                    student.getId(), subject.getId());
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setSubject(subject);
            enrollment.setStatus(Enrollment.STATUS_WAITLISTED);
            enrollment.setEnrollmentDate(LocalDate.now());

            subject.addEnrollment(enrollment);

            enrollmentRepository.save(enrollment);
        }
    }

    @Transactional
    public void dropEnrollment(Long enrollmentId) {
        logger.debug("Attempting to drop enrollment ID: {}", enrollmentId);
        var currentUser = securityService.getCurrentUser();
        if (!(currentUser instanceof Student student)) {
            logger.warn("Attempted to drop enrollment but current user is not a student");
            throw new StudentNotFoundException("Current user is not a student");
        }

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> {
                    logger.error("Enrollment not found with ID: {}", enrollmentId);
                    return new NoSuchElementException("Enrollment not found with ID: " + enrollmentId);
                });

        if (!enrollment.getStudent().getId().equals(student.getId())) {
            logger.warn("Student ID: {} attempted to drop enrollment ID: {} that belongs to student ID: {}",
                    student.getId(), enrollmentId, enrollment.getStudent().getId());
            throw new IllegalStateException("Enrollment doesn't belong to current student");
        }

        logger.info("Dropping enrollment ID: {} for student ID: {}", enrollmentId, student.getId());
        enrollment.setStatus(Enrollment.STATUS_DROPPED);
        enrollmentRepository.save(enrollment);

        Subject subject = enrollment.getSubject();
        if (Enrollment.STATUS_ENROLLED.equals(enrollment.getStatus())) {
            processWaitlistForSubject(subject);
        }
    }

    @Transactional
    public void processWaitlistForSubject(Subject subject) {
        if (hasAvailableCapacity(subject)) {
            // Get waitlisted enrollments ordered by enrollment date (first come first served)
            List<Enrollment> waitlistedEnrollments = enrollmentRepository.findBySubjectAndStatusOrderByEnrollmentDateAsc(
                    subject, Enrollment.STATUS_WAITLISTED);

            if (!waitlistedEnrollments.isEmpty()) {
                Enrollment firstWaitlisted = waitlistedEnrollments.getFirst();
                firstWaitlisted.setStatus(Enrollment.STATUS_ENROLLED);
                enrollmentRepository.save(firstWaitlisted);
                logger.info("Promoted student ID: {} from waitlist to enrolled for subject ID: {}",
                        firstWaitlisted.getStudent().getId(), subject.getId());
            }
        }
    }
}
