package wat.semestr7.ai.services.dataservices;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
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

    public ConcertRoomDto getByName(String name) throws EntityNotFoundException
    {
        ConcertRoom room = concertRoomRepository.findFirstByConcertRoomName(name);
        if(room == null) throw new EntityNotFoundException("There is no concert room with such a name : " + name);
        return mapper.concertRoomtoDto(room);
    }

    public Integer getAmountOfRows() {
        return seatRepository.countByPosition(1);
    }

    public Integer getAmountOfPositions()
    {
        return seatRepository.countByRow(1);
    }
}
