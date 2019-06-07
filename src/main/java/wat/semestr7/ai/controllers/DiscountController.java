package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.DiscountDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.dataservices.DiscountService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DiscountController {
    private DiscountService service;

    public DiscountController(DiscountService service) {
        this.service = service;
    }

    @GetMapping("/discount")
    public ResponseEntity<List<DiscountDto>> getAllDiscounts()
    {
        return ResponseEntity.ok().body(service.getAll());
    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT},value = "/admin/discount")
    public void postDiscount(@RequestBody @Valid DiscountDto dto){
        service.addDiscount(dto);
    }

    @DeleteMapping("/admin/discount/{id}")
    public void deleteDiscount(@PathVariable int id) throws EntityNotFoundException {
        service.delete(id);
    }
}
