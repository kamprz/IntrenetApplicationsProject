package wat.semestr8.tim.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import wat.semestr8.tim.dtos.*;
import wat.semestr8.tim.dtos.finance.ConcertFinanceSummaryDto;
import wat.semestr8.tim.dtos.finance.TransactionDto;
import wat.semestr8.tim.entities.*;
import wat.semestr8.tim.others.ConcertDetails;
import wat.semestr8.tim.utils.DateUtils;

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

    @Mappings({
            @Mapping(source = "concertRoom.concertRoomName", target = "concertRoomName"),
            @Mapping(source = "concertRoom.address", target = "concertRoomAddress"),
            @Mapping(source = "concertRoom.rentCosts", target = "concertRoomRentalCost"),
            @Mapping(source = "concertPerformers.details", target = "concertPerformers"),
            @Mapping(source = "concertPerformers.costOfPersonnel", target = "performersCost"),
            @Mapping(source = "additionalOrganisationCosts", target = "additionalConcertOrganisationCosts"),
            @Mapping(source = "date", target = "date", qualifiedByName = "dateToString"),
            @Mapping(source = "repertoire", target = "repertoire", qualifiedByName = "pomListToDto")
    })
    ConcertFinanceSummaryDto concertToFinanceSummary(Concert concert);


    /*
    @Named("pomDtoToPom")
    @Mapping(target = "titlePiece", source = "title")
    PieceOfMusic dtoToPieceOfMusic(PieceOfMusicDto dto);

    @Named("dtoToPom")
    @Mapping(target = "title", source = "titlePiece")
    PieceOfMusicDto pieceOfMusicToDto(PieceOfMusic pieceOfMusic);

    @Mappings({
            @Mapping(source = "concertRoom.concertRoomName", target = "concertRoomName"),
            @Mapping(source = "concertRoom.address", target = "concertRoomAddress"),
            @Mapping(source = "date", target = "date", qualifiedByName = "dateToString"),
            @Mapping(source = "repertoire", target = "repertoire", qualifiedByName = "pomListToDto")
    })
    ConcertDto concertToDto(Concert concert);

    @Named("pomListToDto")
    default List<PieceOfMusicDto> pomListToDto (List<PieceOfMusic> repertoire){
        List<PieceOfMusicDto> dtos = new LinkedList<>();
        for(PieceOfMusic pom : repertoire){
            dtos.add(pieceOfMusicToDto(pom));
        }
        return dtos;
    }
     */
}
