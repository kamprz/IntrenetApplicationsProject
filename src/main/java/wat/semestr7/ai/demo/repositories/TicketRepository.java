package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Ticket;

public interface TicketRepository extends CrudRepository<Ticket,Integer>
{

}
