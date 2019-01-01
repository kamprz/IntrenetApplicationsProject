package wat.semestr7.ai.controllers;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.PurchaseDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.paypal.CreatedPaymentResponse;
import wat.semestr7.ai.services.paypal.ExecutedPaymentResponse;
import wat.semestr7.ai.services.paypal.PayPalService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PayPalController {

    private final PayPalService payPalService;
    PayPalController(PayPalService payPalService){
        this.payPalService = payPalService;
    }

    /*
    @RequestParam(
     */
    @PostMapping(value = "/paypal/payment/make")
    public ResponseEntity<String> makePayment(@RequestBody PurchaseDto purchaseDto) throws EntityNotFoundException, PayPalRESTException {
        return ResponseEntity.ok().body(payPalService.createPayment(purchaseDto));
    }

    @PostMapping(value = "/paypal/payment/complete")
    public ResponseEntity<String> completePayment(HttpServletRequest request) throws PayPalRESTException {
        return ResponseEntity.ok().body(payPalService.completePayment(request));
    }
}
