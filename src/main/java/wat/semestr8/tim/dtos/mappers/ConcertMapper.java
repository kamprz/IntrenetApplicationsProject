package wat.semestr8.tim.dtos.mappers;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import wat.semestr8.tim.dtos.ConcertDto;
import wat.semestr8.tim.dtos.PieceOfMusicDto;
import wat.semestr8.tim.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr8.tim.entities.Concert;
import wat.semestr8.tim.entities.ConcertRoom;
import wat.semestr8.tim.entities.PieceOfMusic;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.repositories.ConcertRoomRepository;
import wat.semestr8.tim.repositories.PerformersRepository;
import wat.semestr8.tim.services.dataservices.ConcertRoomService;
import wat.semestr8.tim.services.dataservices.PieceOfMusicService;
import wat.semestr8.tim.utils.DateUtils;

import java.util.LinkedList;
import java.util.List;


@Component
public class ConcertMapper {

    private PerformersRepository performersRepository;
    private ConcertRoomRepository concertRoomRepository;
    private PieceOfMusicService pieceOfMusicService;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public ConcertMapper(PerformersRepository performersRepository, ConcertRoomRepository concertRoomRepository, PieceOfMusicService pieceOfMusicService) {
        this.performersRepository = performersRepository;
        this.concertRoomRepository = concertRoomRepository;
        this.pieceOfMusicService = pieceOfMusicService;
    }

    public Concert dtoToConcert(ConcertDto dto) throws EntityNotFoundException {

        Concert concert = mapper.dtoToConcert(dto);

        concert.setConcertRoom(concertRoomRepository.findFirstByConcertRoomName(dto.getConcertRoomName()));
        concert.setConcertPerformers(performersRepository.findFirstByDetails(dto.getConcertPerformers()));
        if(concert.getConcertPerformers() == null) throw new EntityNotFoundException("There is no such performers entity in database.");
        if(concert.getConcertRoom() == null) throw new EntityNotFoundException("There is no such concert room entity in database.");

        List<PieceOfMusic> repertoire = new LinkedList<>();
        for(PieceOfMusicDto pomDto : dto.getRepertoire())
        {
            try {
                repertoire.add(pieceOfMusicService.getByNameAndComposer(pomDto.getTitle(),pomDto.getComposer()));
            }
            catch (EntityNotFoundException e){
                repertoire.add(pieceOfMusicService.createAndReturn(pomDto));
            }
        }
        concert.setRepertoire(repertoire);
        return concert;
    }
}




















