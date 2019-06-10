package wat.semestr8.tim.dtos.finance;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ConcertFinanceSummaryDto
{
    private String concertTitle;
    private String date;
    private String concertRoomName;
    private String concertRoomAddress;
    private String concertPerformers;

    private BigDecimal additionalConcertOrganisationCosts;
    private BigDecimal performersCost;
    private BigDecimal concertRoomRentalCost;
    private BigDecimal ticketCost;

    private int amountOfTicketsSold;
    private BigDecimal incomeFromTickets;
    private BigDecimal balance;
}
