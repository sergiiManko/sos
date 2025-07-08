package pl.atins.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.atins.domain.Student;
import pl.atins.dto.PaymentDTO;
import pl.atins.service.PaymentService;
import pl.atins.service.SecurityService;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class PaymentController {

    private final PaymentService paymentService;
    private final SecurityService securityService;

    @GetMapping
    public String listPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "paymentDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            Model model) {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction);
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

            Page<PaymentDTO> paymentsPage = paymentService.getPaymentsForStudent(student.getId(), pageable);

            model.addAttribute("payments", paymentsPage.getContent());
            model.addAttribute("currentPage", paymentsPage.getNumber());
            model.addAttribute("totalPages", paymentsPage.getTotalPages());
            model.addAttribute("totalItems", paymentsPage.getTotalElements());
            model.addAttribute("sort", sort);
            model.addAttribute("direction", direction);
            model.addAttribute("reverseSortDir", direction.equals("ASC") ? "DESC" : "ASC");
            return "payments";
        }
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable Long id) {
        var currentUser = securityService.getCurrentUser();
        if (currentUser instanceof Student student) {
            return ResponseEntity.ok(paymentService.findById(id, student.getId()));
        }
        return ResponseEntity.notFound().build();
    }
}
