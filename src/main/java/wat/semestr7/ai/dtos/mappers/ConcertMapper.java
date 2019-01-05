package wat.semestr7.ai.dtos.mappers;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import wat.semestr7.ai.dtos.ConcertDto;
import wat.semestr7.ai.dtos.PieceOfMusicDto;
import wat.semestr7.ai.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.entities.PieceOfMusic;
import wat.semestr7.ai.repositories.ConcertRoomRepository;
import wat.semestr7.ai.repositories.PerformersRepository;
import wat.semestr7.ai.utils.DateUtils;

import java.text.ParseException;

@Component
public class ConcertMapper {

    private PerformersRepository performersRepository;
    private ConcertRoomRepository concertRoomRepository;
    private EntityToDtoMapper mapper = Mappers.getMapper(EntityToDtoMapper.class);

    public ConcertMapper(PerformersRepository performersRepository, ConcertRoomRepository concertRoomRepository) {
        this.performersRepository = performersRepository;
        this.concertRoomRepository = concertRoomRepository;
    }

    public Concert dtoToConcert(ConcertDto dto) throws ParseException {
        Concert concert = new Concert();

        concert.setIdConcert(dto.getIdConcert());
        concert.setConcertTitle(dto.getConcertTitle());
        concert.setDate(DateUtils.utcToDate(dto.getDate()));    //this throws ParseException
        concert.setAdditionalOrganisationCosts(dto.getAdditionalOrganisationCosts());
        concert.setTicketCost(dto.getTicketCost());
        concert.setConcertRoom(concertRoomRepository.findFirstByConcertRoomName(dto.getConcertRoomName()));
        concert.setConcertPerformers(performersRepository.findFirstByDetails(dto.getConcertPerformers()));
        concert.setApproved(dto.isApproved());
        for(PieceOfMusicDto pomDto : dto.getRepertoire())
        {
            concert.addPieceOfMusic(mapper.pieceOfMusicDtoToPieceOfMusic(pomDto));
        }
        return concert;
    }

    public ConcertDto concertToDto(Concert concert)
    {
        ConcertDto dto = new ConcertDto();
        dto.setIdConcert(concert.getIdConcert());
        dto.setConcertTitle(concert.getConcertTitle());
        dto.setDate(DateUtils.toUtcString(concert.getDate()));
        dto.setAdditionalOrganisationCosts(concert.getAdditionalOrganisationCosts());
        dto.setTicketCost(concert.getTicketCost());
        dto.setConcertRoomAddress(concert.getConcertRoom().getAddress());
        dto.setConcertRoomName(concert.getConcertRoom().getConcertRoomName());
        dto.setConcertPerformers(concert.getConcertPerformers().getDetails());
        dto.setApproved(concert.isApproved());
        for(PieceOfMusic pom : concert.getRepertoire())
        {
            dto.addPieceOfMusic(mapper.pieceOfMusicToPieceOfMusicDto(pom));
        }
        return dto;
    }

    public static ConcertFinanceSummaryDto concertToFinanceSummarySimpleFieldsMapping(Concert concert)
    {
        ConcertFinanceSummaryDto financeDto = new ConcertFinanceSummaryDto();
        financeDto.setConcertTitle(concert.getConcertTitle());
        financeDto.setDate(DateUtils.formatDate(concert.getDate()));
        financeDto.setConcertRoomAddress(concert.getConcertRoom().getAddress());
        financeDto.setConcertRoomName(concert.getConcertRoom().getConcertRoomName());
        financeDto.setConcertPerformers(concert.getConcertPerformers().getDetails());

        financeDto.setPerformersCost(concert.getConcertPerformers().getCostOfPersonnel());
        financeDto.setAdditionalConcertOrganisationCosts(concert.getAdditionalOrganisationCosts());
        financeDto.setConcertRoomRentalCost(concert.getConcertRoom().getRentCosts());
        financeDto.setTicketCost(concert.getTicketCost());
        return financeDto;
    }
}




















