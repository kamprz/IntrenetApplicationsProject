package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket,Integer>
{

}
