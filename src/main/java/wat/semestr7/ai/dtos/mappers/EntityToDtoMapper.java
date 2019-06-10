package wat.semestr7.ai.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import wat.semestr7.ai.dtos.*;
import wat.semestr7.ai.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr7.ai.dtos.finance.TransactionDto;
import wat.semestr7.ai.entities.*;
import wat.semestr7.ai.others.ConcertDetails;
import wat.semestr7.ai.repositories.ConcertRepository;
import wat.semestr7.ai.repositories.ConcertRoomRepository;
import wat.semestr7.ai.utils.DateUtils;

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

    @Mapping(source = "date", target = "date", qualifiedByName = "utcToDate")
    Concert dtoToConcert(ConcertDto dto);

}
