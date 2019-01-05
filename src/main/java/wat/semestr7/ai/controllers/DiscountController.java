package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.DiscountDto;
import wat.semestr7.ai.exceptions.customexceptions.WrongEntityInRequestBodyException;
import wat.semestr7.ai.services.dataservices.DiscountService;

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
    public void postDiscount(@RequestBody DiscountDto dto) throws WrongEntityInRequestBodyException {
        checkIfRequestBodyIsCorrect(dto);
        service.addDiscount(dto);
    }

    @DeleteMapping("/admin/discount/{id}")
    public void deleteDiscount(@PathVariable String id)
    {
        service.delete(id);
    }

    private void checkIfRequestBodyIsCorrect(DiscountDto dto) throws WrongEntityInRequestBodyException {
        if(dto.getName() == null || dto.getName().isEmpty()) throw new WrongEntityInRequestBodyException("Discount must be named");
    }

}
