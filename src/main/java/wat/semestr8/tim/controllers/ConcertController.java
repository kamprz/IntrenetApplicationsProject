package wat.semestr8.tim.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr8.tim.dtos.ConcertDto;
import wat.semestr8.tim.exceptions.customexceptions.ConcertAlreadyApprovedException;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.exceptions.customexceptions.WrongDateFormatException;
import wat.semestr8.tim.services.dataservices.ConcertService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
public class ConcertController
{
    private ConcertService service;

    public ConcertController(ConcertService service) {
        this.service = service;
    }

    @GetMapping("/concert/approved")
    public ResponseEntity<List<ConcertDto>> getApprovedConcerts()
    {
        return ResponseEntity.ok().body(service.getApprovedConcerts());
    }

    @GetMapping("/concert/not-approved")
    public ResponseEntity<List<ConcertDto>> getNotApprovedConcerts()
    {
        return ResponseEntity.ok().body(service.getNotApprovedConcerts());
    }

    @PutMapping("/concert/approve")
    public void approveConcert(@RequestParam int id) throws EntityNotFoundException {
        service.approveConcert(id);
    }

    @DeleteMapping("/concert/not-approved/{id}")
    public void deleteNotApprovedConcert(@PathVariable int id) throws ConcertAlreadyApprovedException, EntityNotFoundException {
        service.deleteNotApprovedConcert(id);
    }

    @GetMapping(value = "/admin/concert")
    public ResponseEntity<List<ConcertDto>> getAllConcerts()
    {
        return ResponseEntity.ok().body(service.getAllConcertsDto());
    }

    @GetMapping("/admin/concert/{id}")
    public ResponseEntity<ConcertDto> getConcert(@PathVariable Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(service.getConcertDto(id));
    }

    @RequestMapping(value = "/admin/concert", method = {RequestMethod.POST,RequestMethod.PUT})
    public void addOrUpdateConcert(@RequestBody @Valid ConcertDto concertDto) throws ParseException, EntityNotFoundException {
        service.addConcert(concertDto);
    }

    @DeleteMapping(value = "/admin/concert/{id}")
    public void deleteConcert(@PathVariable int id) throws EntityNotFoundException{
        service.deleteConcert(id);
    }
}
