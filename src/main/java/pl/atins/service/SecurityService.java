package pl.atins.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.atins.domain.Student;
import pl.atins.exception.StudentNotFoundException;
import pl.atins.repository.StudentRepository;
import pl.atins.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public Student getCurrentStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        boolean isStudent = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_STUDENT".equals(authority.getAuthority()));

        if (!isStudent) {
            return null;
        }

        String username = authentication.getName();
        Optional<Student> studentOpt = studentRepository.findByEmail(username);

        return studentOpt.orElseGet(() -> userRepository.findByEmail(username)
                .flatMap(user -> studentRepository.findById(user.getId()))
                .orElse(null));

    }

    public Student getCurrentStudentOrThrow() {
        Student student = getCurrentStudent();
        if (student == null) {
            throw new StudentNotFoundException("Current user is not a student");
        }
        return student;
    }
}
