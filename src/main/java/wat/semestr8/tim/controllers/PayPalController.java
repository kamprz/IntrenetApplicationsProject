package wat.semestr8.tim.controllers;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr8.tim.dtos.AndroidTicketDto;
import wat.semestr8.tim.dtos.PurchaseDto;
import wat.semestr8.tim.dtos.RedirectURL;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.exceptions.customexceptions.PaymentTimeoutException;
import wat.semestr8.tim.exceptions.customexceptions.WrongEntityInRequestBodyException;
import wat.semestr8.tim.services.finance.PayPalService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

@RestController
public class PayPalController {
/*
  sandbox kupiec:
  kupiec@tim.wat
  hasło
  user: timwatb3@gmail.com
    hasło: projekttim5

    projekttim@wat.bb
    haslo123

    */
    private PayPalService payPalService;
    PayPalController(PayPalService payPalService){
        this.payPalService = payPalService;
    }

    @PostMapping(value = "/paypal/payment/make", produces = "application/json")
    public ResponseEntity makePayment(@Valid @RequestBody PurchaseDto purchaseDto) throws EntityNotFoundException, PayPalRESTException {
        if(purchaseDto.getEmail() == null && purchaseDto.getUserId() == null) return ResponseEntity.unprocessableEntity().body("Purchase must have set email or userId field.");
        return ResponseEntity.ok().body(payPalService.createPayment(purchaseDto));
    }

    @PostMapping(value = "/paypal/payment/complete", produces = "application/json")
    public ResponseEntity<List<AndroidTicketDto>> completePayment(@NotEmpty @RequestParam String paymentId,
                                                                  @NotEmpty @RequestParam String token,
                                                                  @NotEmpty @RequestParam String PayerID)
            throws PayPalRESTException, EntityNotFoundException, MessagingException, IOException, WrongEntityInRequestBodyException, PaymentTimeoutException {
        return ResponseEntity.ok().body(payPalService.completePayment(paymentId,PayerID,token));
    }
}