package wat.semestr7.ai.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.dtos.ConcertRoomDto;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.dataservices.ConcertRoomService;

@RestController
public class ConcertRoomController
{
    private ConcertRoomService concertRoomService;

    public ConcertRoomController(ConcertRoomService concertRoomService) {
        this.concertRoomService = concertRoomService;
    }

    @GetMapping("/concert-room/rows")
    public ResponseEntity<Integer> getAmountOfRows()
    {
        return ResponseEntity.ok().body(concertRoomService.getAmountOfRows());
    }
    @GetMapping("/concert-room/positions")
    public ResponseEntity<Integer> getAmountOfPositions()
    {
        return ResponseEntity.ok().body(concertRoomService.getAmountOfPositions());
    }

    @GetMapping("/concert-room/{name}")
    public ResponseEntity<ConcertRoomDto> getByName(@PathVariable String name) throws EntityNotFoundException {
        return ResponseEntity.ok().body(concertRoomService.getByName(name.replaceAll("-"," ")));
    }
}