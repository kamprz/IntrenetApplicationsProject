package wat.semestr7.ai.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import wat.semestr7.ai.dtos.*;
import wat.semestr7.ai.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr7.ai.dtos.finance.TransactionDto;
import wat.semestr7.ai.entities.*;
import wat.semestr7.ai.others.ConcertDetails;
import wat.semestr7.ai.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

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

    //TransactionDto
    @Mappings({@Mapping( source = "date", target = "date", qualifiedByName = "dateToString"),
                @Mapping(source = "transactionDetails", target = "transactionDetails")})
    TransactionDto transactionToDto(Transaction transactionDto);

    @Named("dateToString")
    default String dateToString(Date date) {
        return DateUtils.formatDate(date);
    }
}
