package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.services.ConcertRoomService;

@RestController
public class ConcertRoomController
{
    private ConcertRoomService concertRoomService;

    public ConcertRoomController(ConcertRoomService concertRoomService) {
        this.concertRoomService = concertRoomService;
    }
}
