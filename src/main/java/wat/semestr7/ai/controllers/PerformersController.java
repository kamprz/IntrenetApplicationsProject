package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.PerformersDto;
import wat.semestr7.ai.exceptions.customexceptions.WrongEntityInRequestBodyException;
import wat.semestr7.ai.services.dataservices.PerformersService;

import java.util.List;

@RestController
public class PerformersController
{
    private PerformersService service;

    public PerformersController(PerformersService service) {
        this.service = service;
    }

    @GetMapping(value = "/admin/performer")
    public ResponseEntity<List<PerformersDto>> getAllPerformers()
    {
        return ResponseEntity.ok().body(service.getAll());
    }

    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT},value = "/admin/performer")
    public void postPerformers(@RequestBody PerformersDto dto) throws WrongEntityInRequestBodyException {
        checkIfRequestBodyIsCorrect(dto);
        service.create(dto);
    }

    @DeleteMapping(value = "/admin/performer")
    public void deletePerformers(@RequestParam() int id)
    {
        service.delete(id);
    }

    private void checkIfRequestBodyIsCorrect(PerformersDto dto) throws WrongEntityInRequestBodyException {
        if(dto.getDetails() == null || dto.getDetails().isEmpty()) throw new WrongEntityInRequestBodyException("Details of performers are not set");

    }
}
