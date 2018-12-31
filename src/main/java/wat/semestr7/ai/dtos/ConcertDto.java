package wat.semestr7.ai.dtos.concert;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import wat.semestr7.ai.dtos.pieceofmusic.PieceOfMusicDto;
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
    private Date date;
    private BigDecimal additionalOrganisationCosts;
    private BigDecimal ticketCost;
    private ConcertRoom concertRoom;
    private Performers concertPerformers;
    private List<PieceOfMusicDto> repertoire = new LinkedList<>();
    public void addPieceOfMusic(PieceOfMusicDto p){ repertoire.add(p);}
}
