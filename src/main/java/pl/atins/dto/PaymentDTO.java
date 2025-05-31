package pl.atins.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.atins.domain.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String referenceCode;
    private String status;
    private String title;

    public static PaymentDTO fromEntity(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .referenceCode(payment.getReferenceCode())
                .status(payment.getStatus())
                .title(payment.getTitle())
                .build();
    }
}
