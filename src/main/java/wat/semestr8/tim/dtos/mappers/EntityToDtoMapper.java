package wat.semestr8.tim.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import wat.semestr8.tim.dtos.*;
import wat.semestr8.tim.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr8.tim.dtos.finance.TransactionDto;
import wat.semestr8.tim.entities.*;
import wat.semestr8.tim.model.ConcertDetails;
import wat.semestr8.tim.model.SeatOccupied;
import wat.semestr8.tim.utils.DateUtils;


import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface EntityToDtoMapper {

    //ConcertRoom
    ConcertRoomDto concertRoomtoDto(ConcertRoom concertRoom);

    @Mapping( source = "date", target = "date", qualifiedByName = "dateToString")
    ConcertDetailsDto detailsToDto(ConcertDetails details);

    //Discount
    Discount dtoToDiscount(DiscountDto dto);
    DiscountDto discountToDto(Discount discount);

    //Performers
    Performers dtoToPerformers(PerformersDto dto);
    PerformersDto performersToDto(Performers performers);

    //Piece of Music:
    @Mapping(target = "titlePiece", source = "title")
    PieceOfMusic dtoToPieceOfMusic(PieceOfMusicDto dto);

    @Mapping(target = "title", source = "titlePiece")
    PieceOfMusicDto pieceOfMusicToDto(PieceOfMusic pieceOfMusic);

    @Named("dateToString")
    default String dateToString(Date date) {
        return DateUtils.formatDate(date);
    }

    @Mappings({
            @Mapping(source = "concertRoom.concertRoomName", target = "concertRoomName"),
            @Mapping(source = "concertRoom.address", target = "concertRoomAddress"),
            @Mapping(source = "concertRoom.rentCosts", target = "concertRoomRentalCost"),
            @Mapping(source = "concertPerformers.details", target = "concertPerformers"),
            @Mapping(source = "concertPerformers.costOfPersonnel", target = "performersCost"),
            @Mapping(source = "additionalOrganisationCosts", target = "additionalConcertOrganisationCosts"),
            @Mapping(source = "date", target = "date", qualifiedByName = "dateToString")//,
           // @Mapping(source = "repertoire", target = "repertoire", qualifiedByName = "pomListToDto")
    })
    ConcertFinanceSummaryDto concertToFinanceSummary(Concert concert);

    //TransactionDto
    @Mappings({@Mapping( source = "date", target = "date", qualifiedByName = "dateToString"),
                @Mapping(source = "transactionDetails", target = "transactionDetails")})
    TransactionDto transactionToDto(Transaction transactionDto);



    @Named("repertoireToDto")
    default List<PieceOfMusicDto> repertoireToDto (List<PieceOfMusic> repertoire){
        List<PieceOfMusicDto> dtos = new LinkedList<>();
        for(PieceOfMusic pom : repertoire){
            dtos.add(pieceOfMusicToDto(pom));
        }
        return dtos;
    }

    @Named("dateToUtcString")
    default String dateToUtcString(Date date) { return DateUtils.toUtcString(date); }

    @Mappings({
            @Mapping(source = "date", target = "date", qualifiedByName = "dateToUtcString"),
            @Mapping(source = "concertRoom.address", target = "concertRoomAddress"),
            @Mapping(source = "concertRoom.concertRoomName", target = "concertRoomName"),
            @Mapping(source = "concertPerformers.details", target = "concertPerformers"),
            @Mapping(source = "repertoire", target = "repertoire", qualifiedByName = "repertoireToDto")
          // ? approved
    })
    ConcertDto concertToDto(Concert c);

    @Named("utcToDate")
    default Date utcToDate(String utc) throws ParseException { return DateUtils.utcToDate(utc); }

    @Mappings({
            @Mapping(source = "date", target = "date", qualifiedByName = "utcToDate"),
            @Mapping(target = "concertPerformers",ignore = true)
    })

    Concert dtoToConcert(ConcertDto dto);

    @Mapping( target = "unlockingCountdownStarts", ignore = true)
    SeatOccupied seatDtoToSeatOccupied(SeatDto seat);

    SeatDto occupiedToDto(SeatOccupied occupied);





   /*
    private int row;
    private int position;*/
   @Mappings({
           @Mapping(source = "concert.date", target = "date", qualifiedByName = "dateToString"),
           @Mapping(source = "concert.concertTitle", target = "concertTitle"),
           @Mapping(source = "concert.ticketCost", target = "cost"),
           @Mapping(source = "discount.name", target = "discount"),
           @Mapping(source = "concert.concertRoom.concertRoomName", target = "concertRoom"),
           @Mapping(source = "seat.row", target = "row"),
           @Mapping(source = "seat.position", target = "position")
   })
    AndroidTicketDto ticketForAndroid(Ticket ticket);

}
