package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.exceptions.customexceptions.ConcertAlreadyApprovedException;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.exceptions.customexceptions.WrongDateFormatException;
import wat.semestr7.ai.services.dataservices.ConcertService;

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
    public void addOrUpdateConcert(@RequestBody ConcertDto concertDto) throws ParseException, WrongDateFormatException, EntityNotFoundException {
        checkIfRequestBodyIsCorrect(concertDto);
        service.addConcert(concertDto);
    }

    @DeleteMapping(value = "/admin/concert/{id}")
    public void deleteConcert(@PathVariable int id) throws EntityNotFoundException{
        service.deleteConcert(id);
    }

    private void checkIfRequestBodyIsCorrect(@Valid ConcertDto dto) throws WrongDateFormatException {
        final String dateFormatRegex = "\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3} UTC";
        if(!dto.getDate().matches(dateFormatRegex)) throw new WrongDateFormatException("Wrong date format. Used one is: dd/MM/yyyy HH:mm");
    }
}
