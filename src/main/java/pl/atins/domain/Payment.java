package pl.atins.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment extends BaseEntity {
    private Double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String referenceCode;
    private String status;
    private String title;
}
