package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.PaymentRepository;

@Service
public class PaymentService
{
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
}
