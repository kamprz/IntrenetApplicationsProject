package wat.semestr8.tim.repositories;


import org.springframework.data.repository.CrudRepository;
import wat.semestr8.tim.entities.Discount;

import java.util.List;

public interface DiscountRepository extends CrudRepository<Discount,Integer>{
    Discount getDiscountByName(String name);

    List<Discount> findAll();
}
