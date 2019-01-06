package wat.semestr7.ai.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import wat.semestr7.ai.dtos.*;
import wat.semestr7.ai.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr7.ai.dtos.finance.TransactionDto;
import wat.semestr7.ai.entities.*;
import wat.semestr7.ai.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

@Mapper
public interface EntityToDtoMapper {

    //ConcertRoom
    ConcertRoom dtoToConcertRoom(ConcertRoomDto dto);
    ConcertRoomDto concertRoomtoDto(ConcertRoom concertRoom);

    //Concert details
    /*@Mapping(source = "date", target = "date", qualifiedByName = "stringToDate" )
    ConcertDetailsDto concertToDetails(Concert concert);
    @Mapping(source = "date", target = "date", qualifiedByName = "dateToString")
    Concert detailsToConcert(ConcertDetailsDto detailsDto);*/

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

    //Seat
    /*Seat dtoToSeat(SeatDto dto);
    SeatDto seatToDto(Seat seat);*/

    //TransactionDto
    @Mapping(source = "date", target = "date", qualifiedByName = "stringToDate" )
    Transaction dtoToTransaction(TransactionDto dto);
    @Mapping(source = "date", target = "date", qualifiedByName = "dateToString")
    TransactionDto transactionToDto(Transaction transactionDto);

    @Named("dateToString")
    default String dateToString(Date date) {
        return DateUtils.formatDate(date);
    }

    @Named("stringToDate")
    default Date stringToDate(String str)
    {
        try {
            return DateUtils.parseDate(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
