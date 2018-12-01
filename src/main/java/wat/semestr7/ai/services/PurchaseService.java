package wat.semestr7.ai.services;

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
}
