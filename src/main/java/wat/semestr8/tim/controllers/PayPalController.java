package wat.semestr8.tim.controllers;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr8.tim.dtos.PurchaseDto;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.exceptions.customexceptions.PaymentTimeoutException;
import wat.semestr8.tim.exceptions.customexceptions.WrongEntityInRequestBodyException;
import wat.semestr8.tim.services.finance.PayPalService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class PayPalController {

    private PayPalService payPalService;
    PayPalController(PayPalService payPalService){
        this.payPalService = payPalService;
    }

    @PostMapping(value = "/paypal/payment/make")
    public ResponseEntity<String> makePayment(@RequestBody PurchaseDto purchaseDto) throws EntityNotFoundException, PayPalRESTException {
        return ResponseEntity.ok().body(payPalService.createPayment(purchaseDto));
    }

    @PostMapping(value = "/paypal/payment/complete")
    public ResponseEntity<String> completePayment(HttpServletRequest request) throws PayPalRESTException, EntityNotFoundException, MessagingException, IOException, WrongEntityInRequestBodyException, PaymentTimeoutException {
        checkIfCompletingPaymentRequestBodyIsCorrect(request);
        return ResponseEntity.ok().body(payPalService.completePayment(request));
    }

    private void checkIfCompletingPaymentRequestBodyIsCorrect(HttpServletRequest request) throws WrongEntityInRequestBodyException {
        if(request.getParameter("paymentId")==null) throw new WrongEntityInRequestBodyException("PaymentId not present int request");
        if(request.getParameter("token")==null) throw new WrongEntityInRequestBodyException("Token not present int request");
        if(request.getParameter("PayerID")==null) throw new WrongEntityInRequestBodyException("PayerID not present int request");
    }

}