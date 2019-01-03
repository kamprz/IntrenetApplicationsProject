package wat.semestr7.ai.controllers;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.PurchaseDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.exceptions.customexceptions.WrongEntityInRequestBodyException;
import wat.semestr7.ai.services.paypal.PayPalService;

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
    public ResponseEntity<String> makePayment(@RequestBody PurchaseDto purchaseDto) throws EntityNotFoundException, PayPalRESTException, WrongEntityInRequestBodyException {
        checkIfMakingPaymentRequestBodyIsCorrect(purchaseDto);
        return ResponseEntity.ok().body(payPalService.createPayment(purchaseDto));
    }

    @PostMapping(value = "/paypal/payment/complete")
    public ResponseEntity<String> completePayment(HttpServletRequest request) throws PayPalRESTException, EntityNotFoundException, MessagingException, IOException, WrongEntityInRequestBodyException {
        checkIfCompletingPaymentRequestBodyIsCorrect(request);
        return ResponseEntity.ok().body(payPalService.completePayment(request));
    }


    private void checkIfMakingPaymentRequestBodyIsCorrect(PurchaseDto dto) throws WrongEntityInRequestBodyException {
        if(dto.getTickets() == null || dto.getTickets().isEmpty()) throw new WrongEntityInRequestBodyException("There must be any ticket assigned to this purchase");
        if(dto.getConcertId() == null) throw new WrongEntityInRequestBodyException("Purchase must be assigned to a concert");
        if(dto.getEmail() == null || dto.getEmail().isEmpty()) throw new WrongEntityInRequestBodyException("There must be an email assigned to a purchase");
    }

    private void checkIfCompletingPaymentRequestBodyIsCorrect(HttpServletRequest request) throws WrongEntityInRequestBodyException {
        if(request.getParameter("paymentId")==null) throw new WrongEntityInRequestBodyException("PaymentId not present int request");
        if(request.getParameter("token")==null) throw new WrongEntityInRequestBodyException("Token not present int request");
        if(request.getParameter("PayerID")==null) throw new WrongEntityInRequestBodyException("PayerID not present int request");
    }

}