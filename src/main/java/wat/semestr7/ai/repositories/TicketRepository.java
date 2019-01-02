package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Purchase;
import wat.semestr7.ai.entities.Ticket;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket,Integer>
{
    Ticket findFirstByPurchase(Purchase purchase);

    List<Ticket> findAllByPurchase(Purchase purchase);
}
