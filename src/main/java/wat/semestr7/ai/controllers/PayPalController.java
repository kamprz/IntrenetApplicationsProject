package com.example.demo.controller;

import com.example.demo.service.CreatedPaymentResponse;
import com.example.demo.service.ExecutedPaymentResponse;
import com.example.demo.service.PayPalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping
public class PayPalController {

    private final PayPalClient payPalClient;
    @Autowired
    PayPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }

    @PostMapping(value = "/paypal/make/payment")
    public CreatedPaymentResponse makePayment(@RequestParam("sum") String sum){
        return payPalClient.createPayment(sum);
    }

    @PostMapping(value = "/paypal/complete/payment")
    public ExecutedPaymentResponse completePayment(HttpServletRequest request){
        ExecutedPaymentResponse e =  payPalClient.completePayment(request);
        System.out.println(e.getPayment());
        System.out.println(e.getStatus());
        return e;
    }

    @GetMapping(value = "/success")
    public void theEnd(HttpServletRequest request)
    {
        ExecutedPaymentResponse e =  payPalClient.completePayment(request);
    }

}
