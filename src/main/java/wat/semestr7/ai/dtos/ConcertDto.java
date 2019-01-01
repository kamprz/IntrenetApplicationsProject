package wat.semestr7.ai.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import wat.semestr7.ai.entities.ConcertRoom;
import wat.semestr7.ai.entities.Performers;
import wat.semestr7.ai.entities.PieceOfMusic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ConcertDto
{
    private int idConcert;
    private String concertTitle;
    private String date;
    private BigDecimal additionalOrganisationCosts;
    private boolean isApproved;
    private BigDecimal ticketCost;
    private String concertRoomName;
    private String concertRoomAddress;
    private String concertPerformers;
    private List<PieceOfMusicDto> repertoire = new LinkedList<>();
    public void addPieceOfMusic(PieceOfMusicDto p){ repertoire.add(p);}
}