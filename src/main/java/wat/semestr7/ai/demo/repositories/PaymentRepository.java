package wat.semestr7.ai.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.demo.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>
{

}
