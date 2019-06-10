package wat.semestr8.tim.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.entities.Ticket;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket,Integer>
{
    Ticket findFirstByPurchase(Purchase purchase);

    List<Ticket> findAllByPurchase(Purchase purchase);

    @Query(value = "select new wat.semestr8.tim.entities.Ticket(t.idTicket,t.discount,t.concert,t.seat,t.purchase) " +
            " from Ticket t" +
            " left join t.concert c" +
            " left join t.purchase p" +
            " where c.idConcert = :concertId" +
            " and p.isPaid = true")
    List<Ticket> findAllPaidByConcert(@Param("concertId") int concertId);
}
