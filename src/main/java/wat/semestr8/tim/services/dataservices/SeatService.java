package wat.semestr8.tim.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.entities.Seat;
import wat.semestr8.tim.repositories.SeatRepository;

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
