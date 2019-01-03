package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.exceptions.customexceptions.WrongDateFormatException;
import wat.semestr7.ai.exceptions.customexceptions.WrongEntityInRequestBodyException;
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

    @GetMapping(value = "/concert")
    public ResponseEntity<List<ConcertDto>> getAllConcerts()
    {
        return ResponseEntity.ok().body(service.getAllConcerts());
    }

    @GetMapping("/admin/concert/{id}")
    public ResponseEntity<ConcertDto> getConcert(@PathVariable Integer id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(service.getConcertDto(id));
    }

    @RequestMapping(value = "/admin/concert", method = {RequestMethod.POST,RequestMethod.PUT})
    public void addOrUpdateConcert(@RequestBody ConcertDto concertDto) throws ParseException, WrongEntityInRequestBodyException, WrongDateFormatException {
        checkIfRequestBodyIsCorrect(concertDto);
        service.addConcert(concertDto);
    }

    @GetMapping("/concert/not-approved")
    public ResponseEntity<List<ConcertDto>> getNotApprovedConcerts()
    {
        return ResponseEntity.ok().body(service.getNotApprovedConcerts());
    }

    @GetMapping("/concert/approved")
    public ResponseEntity<List<ConcertDto>> getApprovedConcerts()
    {
        return ResponseEntity.ok().body(service.getApprovedConcerts());
    }

    @PostMapping("/concert/approve")
    public void approveConcert(@RequestParam int id) throws EntityNotFoundException {
        service.approveConcert(id);
    }

    @DeleteMapping(value = "/admin/concert")
    public void deleteConcert(@RequestParam int id) throws EntityNotFoundException{
        service.deleteConcert(id);
    }

    private void checkIfRequestBodyIsCorrect(ConcertDto dto) throws WrongEntityInRequestBodyException, WrongDateFormatException {
        if(dto.getConcertPerformers() == null || dto.getConcertPerformers().isEmpty()) throw new WrongEntityInRequestBodyException("Performers of a concert must be set");
        if(dto.getConcertRoomName() == null || dto.getConcertRoomName().isEmpty()) throw new WrongEntityInRequestBodyException("Concert room must be set");
        if(dto.getConcertTitle() == null || dto.getConcertTitle().isEmpty()) throw new WrongEntityInRequestBodyException("Concert title must be set");
        if(dto.getConcertRoomAddress() == null || dto.getConcertRoomAddress().isEmpty()) throw new WrongEntityInRequestBodyException("Concert room address must be set");
        if(dto.getDate() == null || dto.getConcertRoomAddress().isEmpty()) throw new WrongEntityInRequestBodyException("Date of a concert must be set");
        if(!dto.getDate().matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}")) throw new WrongDateFormatException("Wrong date format. Used one is: dd/MM/yyyy HH:mm");
        if(dto.getTicketCost() == null) throw new WrongEntityInRequestBodyException("Ticket price must be set for a concert");
        if(dto.getRepertoire() == null || dto.getRepertoire().size()==0) throw new WrongEntityInRequestBodyException("Concert must has a repertoire");
    }
}
