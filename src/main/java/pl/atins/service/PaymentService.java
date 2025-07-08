package pl.atins.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.atins.domain.Payment;
import pl.atins.dto.PaymentDTO;
import pl.atins.repository.PaymentRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentDTO findById(Long paymentId, Long studentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoSuchElementException("Payment not found"));

        if (!payment.getStudent().getId().equals(studentId)) {
            throw new AccessDeniedException("You are not allowed to view this payment");

        }
        return PaymentDTO.fromEntity(payment);
    }

    public Page<PaymentDTO> getPaymentsForStudent(Long studentId, Pageable pageable) {
        return paymentRepository
                .findByStudentId(studentId, pageable)
                .map(PaymentDTO::fromEntity);
    }

}
