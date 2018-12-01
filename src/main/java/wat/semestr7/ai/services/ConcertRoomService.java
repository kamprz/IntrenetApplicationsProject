package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.ConcertRoomRepository;

@Service
public class ConcertRoomService
{
    private ConcertRoomRepository concertRoomRepository;

    public ConcertRoomService(ConcertRoomRepository concertRoomRepository) {
        this.concertRoomRepository = concertRoomRepository;
    }
}
