package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.FreeSeatDTO;
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

    public List<FreeSeatDTO> getFreeSeatsOnConcert(int idConcert)
    {
        return seatRepository.getAllFreeSeatsOnConcert(idConcert);
    }
    public Seat getSeatByRowAndPosition(int row, int position)
    {
        return seatRepository.findFirstByRowAndPosition(row,position);
    }
}
