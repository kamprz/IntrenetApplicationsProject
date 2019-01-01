package wat.semestr7.ai.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wat.semestr7.ai.dtos.*;
import wat.semestr7.ai.entities.*;

@Mapper
public interface EntityToDtoMapper {

    /*
    //Concerts:
    @Mappings({
            @Mapping( target = "repertoire", ignore = true ),
            @Mapping( source = "concertPerformers", target = "concertPerformers.details")
    })

    Concert concertDtoToConcert(ConcertDto dto);
    @Mappings({
            @Mapping( target = "repertoire", ignore = true ),
            @Mapping( target = "concertPerformers", source = "concertPerformers.details")
    })
    ConcertDto concertToConcertDto(Concert concert);
*/
    //ConcertRoom
    ConcertRoom dtoToConcertRoom(ConcertRoomDto dto);
    ConcertRoomDto concertRoomtoDto(ConcertRoom concertRoom);

    //Performers

    Performers dtoToPerformers(PerformersDto dto);

    PerformersDto performersToDto(Performers performers);

    //Piece of Music:
    @Mapping(target = "titlePiece", source = "title")
    PieceOfMusic pieceOfMusicDtoToPieceOfMusic(PieceOfMusicDto dto);
    @Mapping(target = "title", source = "titlePiece")
    PieceOfMusicDto pieceOfMusicToPieceOfMusicDto(PieceOfMusic pieceOfMusic);


    //Seat

    Seat dtoToSeat(SeatDto dto);

    SeatDto seatToDto(Seat seat);


    //TransactionDto

    Transaction dtoToTransaction(TransactionDto dto);

    TransactionDto transactionToDto(TransactionDto transactionDto);

}
