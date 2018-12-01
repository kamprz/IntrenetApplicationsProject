package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.entities.Payment;
import wat.semestr7.ai.services.PaymentService;

@RestController
public class PaymentController
{
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
