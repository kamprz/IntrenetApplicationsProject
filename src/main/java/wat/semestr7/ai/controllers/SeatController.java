package wat.semestr7.ai.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.dtos.FreeSeatDTO;
import wat.semestr7.ai.entities.Seat;
import wat.semestr7.ai.services.SeatService;

import java.util.List;

@RestController
public class SeatController
{
    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("concerts/{id}/freeSeats")
    public ResponseEntity<List<FreeSeatDTO>> getFreeSeatsOnConcert(@PathVariable("id") int concertId)
    {
        System.out.println(seatService.getSeatByFloorRowPosition(0,1,1));
        return new ResponseEntity<>(seatService.getFreeSeatsOnConcert(concertId), HttpStatus.OK);
    }
}
