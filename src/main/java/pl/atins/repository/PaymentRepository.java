package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
