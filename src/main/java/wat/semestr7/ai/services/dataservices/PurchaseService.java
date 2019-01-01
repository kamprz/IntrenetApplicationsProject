package wat.semestr7.ai.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.Purchase;
import wat.semestr7.ai.repositories.PurchaseRepository;

@Service
public class PurchaseService
{
    private PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public void setPurchasePaidByPaypalToken(String token)
    {
        Purchase purchase = purchaseRepository.findFirstByPaypalID(token);
        purchase.setPaid(true);
        purchaseRepository.save(purchase);
    }

    public Purchase getPurchaseByToken(String token)
    {
        return purchaseRepository.findFirstByPaypalID(token);
    }

    public void setPurchasePaid(Purchase purchase)
    {
        purchase.setPaid(true);
        purchaseRepository.save(purchase);
    }

    
}
