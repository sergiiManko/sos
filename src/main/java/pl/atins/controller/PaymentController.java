package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.atins.domain.Payment;
import pl.atins.dto.PaymentDTO;
import pl.atins.service.PaymentService;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String listPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "paymentDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            Model model) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Page<PaymentDTO> paymentsPage = paymentService.getAllPayments(pageable);

        model.addAttribute("payments", paymentsPage.getContent());
        model.addAttribute("currentPage", paymentsPage.getNumber());
        model.addAttribute("totalPages", paymentsPage.getTotalPages());
        model.addAttribute("totalItems", paymentsPage.getTotalElements());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("reverseSortDir", direction.equals("ASC") ? "DESC" : "ASC");

        return "payments";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        return ResponseEntity.ok(PaymentDTO.fromEntity(payment));
    }
}
