package wat.semestr7.ai.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>
{

}
