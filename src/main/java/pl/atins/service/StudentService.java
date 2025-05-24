package pl.atins.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atins.domain.Student;
import pl.atins.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
