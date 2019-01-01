package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.entities.Discount;
import wat.semestr7.ai.services.dataservices.DiscountService;

import java.util.List;

@RestController
public class DiscountController {
    private DiscountService service;

    @GetMapping("/discount")
    public ResponseEntity<List<Discount>> getAllDiscounts()
    {
        return ResponseEntity.ok().body(service.getAll());
    }
}
