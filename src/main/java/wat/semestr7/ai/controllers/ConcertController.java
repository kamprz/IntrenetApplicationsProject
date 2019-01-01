package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.dataservices.ConcertService;

import java.text.ParseException;
import java.util.List;

@RestController
public class ConcertController
{
    private ConcertService service;

    public ConcertController(ConcertService service) {
        this.service = service;
    }

    @GetMapping("/concerts")
    public ResponseEntity<List<ConcertDto>> getAllConcerts()
    {
        return ResponseEntity.ok().body(service.getAllConcerts());
    }

    @GetMapping("/concerts/{id}")
    public ResponseEntity<ConcertDto> getConcert(@PathVariable Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(service.getConcertDto(id));
    }

    @PutMapping("/concerts")
    public void updateConcert(@RequestBody ConcertDto dto) throws ParseException {
        service.updateConcert(dto);
    }

    @PostMapping("/concerts")
    public void addConcert(@RequestBody ConcertDto concertDto) throws ParseException {
        System.out.println("POST controller");
        service.addConcert(concertDto);
    }

    @GetMapping("/concerts/approve")
    public ResponseEntity<List<ConcertDto>> getNotApprovedConcerts()
    {
        return ResponseEntity.ok().body(service.getNotApprovedConcerts());
    }

}
