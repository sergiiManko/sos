package pl.atins.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.Payment;
import pl.atins.dto.PaymentDTO;
import pl.atins.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(PaymentDTO::fromEntity);
    }
}
