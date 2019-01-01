package wat.semestr7.ai.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.SeatDto;
import wat.semestr7.ai.entities.Seat;
import wat.semestr7.ai.repositories.SeatRepository;

import java.util.List;

@Service
public class SeatService
{
    private SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatDto> getFreeSeatsOnConcert(int idConcert)
    {
        return seatRepository.getAllFreeSeatsOnConcert(idConcert);
    }
    public Seat getSeatByRowAndPosition(int row, int position)
    {
        return seatRepository.findFirstByRowAndPosition(row,position);
    }
}
