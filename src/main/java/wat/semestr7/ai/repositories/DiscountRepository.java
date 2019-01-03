package wat.semestr7.ai.repositories;


import org.springframework.data.repository.CrudRepository;
import wat.semestr7.ai.entities.Discount;

import java.util.List;

public interface DiscountRepository extends CrudRepository<Discount,String>{
    Discount getDiscountByName(String name);

    List<Discount> findAll();
}
