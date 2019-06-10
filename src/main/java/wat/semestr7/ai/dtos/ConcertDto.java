package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import wat.semestr7.ai.entities.ConcertRoom;
import wat.semestr7.ai.entities.Performers;
import wat.semestr7.ai.entities.PieceOfMusic;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ConcertDto
{
    @NotNull
    private int idConcert;
    @NotBlank
    private String concertTitle;
    private String date;

    private BigDecimal additionalOrganisationCosts;
    private boolean isApproved;
    @NotNull
    private BigDecimal ticketCost;
    private String concertRoomName;
    private String concertRoomAddress;
    @NotBlank
    private String concertPerformers;
    @NotEmpty
    private List<PieceOfMusicDto> repertoire = new LinkedList<>();
}