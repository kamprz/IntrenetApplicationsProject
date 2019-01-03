package wat.semestr7.ai.dtos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wat.semestr7.ai.dtos.*;
import wat.semestr7.ai.entities.*;

@Mapper
public interface EntityToDtoMapper {

    //ConcertRoom
    ConcertRoom dtoToConcertRoom(ConcertRoomDto dto);
    ConcertRoomDto concertRoomtoDto(ConcertRoom concertRoom);

    //Discount
    Discount dtoToDiscount(DiscountDto dto);
    DiscountDto discountToDto(Discount discount);

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
