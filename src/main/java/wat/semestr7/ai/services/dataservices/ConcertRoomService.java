package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.repositories.ConcertRoomRepository;
import wat.semestr7.ai.repositories.SeatRepository;

@Service
public class ConcertRoomService
{
    private ConcertRoomRepository concertRoomRepository;
    private SeatRepository seatRepository;

    public ConcertRoomService(ConcertRoomRepository concertRoomRepository, SeatRepository seatRepository) {
        this.concertRoomRepository = concertRoomRepository;
        this.seatRepository = seatRepository;
    }

    public Integer getAmountOfRows() {
        return seatRepository.countByPosition(1);
    }

    public Integer getAmountOfPositions()
    {
        return seatRepository.countByRow(1);
    }
}
