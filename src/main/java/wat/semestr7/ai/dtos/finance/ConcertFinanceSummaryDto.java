package wat.semestr7.ai.dtos.finance;

import lombok.Data;
import lombok.NoArgsConstructor;
import wat.semestr7.ai.dtos.PieceOfMusicDto;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class ConcertFinanceSummaryDto
{
    private String concertTitle;
    private String date;
    private String concertRoomName;
    private String concertRoomAddress;
    private String concertPerformers;

    private BigDecimal ticketCost;
    private BigDecimal additionalConcertOrganisationCosts;
    private BigDecimal performersCost;
    private BigDecimal concertRoomRentalCost;

    private int amountOfTicketsSold;
    private BigDecimal incomeFromTickets;
}
