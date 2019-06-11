package wat.semestr8.tim.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.SeatDto;
import wat.semestr8.tim.entities.Seat;
import wat.semestr8.tim.repositories.SeatRepository;
import wat.semestr8.tim.socket.service.SocketService;

import java.util.List;

@Service
public class SeatService
{
    private SeatRepository seatRepository;
    private SocketService socketService;

    public SeatService(SeatRepository seatRepository, SocketService socketService) {
        this.seatRepository = seatRepository;
        this.socketService = socketService;
    }

    public List<SeatDto> getFreeSeatsOnConcert(int idConcert)
    {
        List<SeatDto> freeSeatsOnConcert = seatRepository.getAllFreeSeatsOnConcert(idConcert);
        for(SeatDto seat: socketService.getCurrentlyOccupied(idConcert)){
            freeSeatsOnConcert.remove(seat);
        }
        return freeSeatsOnConcert;

    }
    public Seat getSeatByRowAndPosition(int row, int position)
    {
        return seatRepository.findFirstByRowAndPosition(row,position);
    }

    public List<SeatDto> getSeatsOccupiedOnConcert(int idConcert)
    {
        return seatRepository.getSeatsOccupiedOnConcert(idConcert);
    }
}
