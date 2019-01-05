package wat.semestr7.ai.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.dtos.ConcertRoomDto;
import wat.semestr7.ai.dtos.mappers.EntityToDtoMapper;
import wat.semestr7.ai.entities.ConcertRoom;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.repositories.ConcertRoomRepository;
import wat.semestr7.ai.repositories.SeatRepository;

@Service
public class ConcertRoomService
{
    private ConcertRoomRepository concertRoomRepository;
    private SeatRepository seatRepository;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public ConcertRoomService(ConcertRoomRepository concertRoomRepository, SeatRepository seatRepository)
    {
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

    public ConcertRoomDto getById(int id) throws EntityNotFoundException
    {
        return mapper.concertRoomtoDto(concertRoomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Such concert room does not exist")));
    }
    public ConcertRoom getConcertRoom() throws EntityNotFoundException {
        return concertRoomRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("There is no concert room in database."));
    }
}