package wat.semestr7.ai.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.dtos.SeatDto;
import wat.semestr7.ai.services.dataservices.SeatService;

import java.util.List;

@RestController
public class SeatController
{
    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/free-seat/{concertId}")
    public ResponseEntity<List<SeatDto>> getFreeSeatsOnConcert(@PathVariable("concertId") int concertId)
    {
        return new ResponseEntity<>(seatService.getFreeSeatsOnConcert(concertId), HttpStatus.OK);
    }

}
