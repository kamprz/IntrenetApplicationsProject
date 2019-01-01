package wat.semestr7.ai.services.dataservices;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.Discount;
import wat.semestr7.ai.repositories.DiscountRepository;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;

@Service
public class DiscountService {
    private DiscountRepository repo;

    public DiscountService(DiscountRepository repo) {
        this.repo = repo;
    }

    public List<Discount> getAll() {
        List<Discount> list = new LinkedList<>();
        for(Discount d : repo.findAll()) list.add(d);
        return list;
    }

    public int getDiscountPercentsByName(String name)
    {
        return repo.getDiscountByName(name).getPercents();
    }

    public void addDiscount(Discount discount)
    {
        repo.save(discount);
    }

    public Discount getByName(String name) throws EntityNotFoundException
    {
        return repo.findById(name).orElseThrow(() -> new EntityNotFoundException());
    }
}
