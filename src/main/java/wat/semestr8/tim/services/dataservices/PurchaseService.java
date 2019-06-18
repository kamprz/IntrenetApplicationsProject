package wat.semestr8.tim.services.dataservices;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.exceptions.customexceptions.PaymentTimeoutException;
import wat.semestr8.tim.repositories.PurchaseRepository;
import wat.semestr8.tim.utils.DateUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService
{
    private PurchaseRepository purchaseRepository;
    private static final int MINUTES_BEFORE_TO_DELETE = 5;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Scheduled(cron = "* */3 * * * *")
    public void deletePurchasesWhichWerentPaid() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        Calendar startDay = Calendar.getInstance();
        startDay.set(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DATE),
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE) - MINUTES_BEFORE_TO_DELETE);

        List<Purchase> purchasesToDelete = purchaseRepository.findAllWhereIsNotPaid()
                .stream()
                .filter(p -> {
                    Calendar c = Calendar.getInstance();
                    c.setTime(p.getTimestamp());
                    if (c.compareTo(startDay) < 0) return true;
                    else return false;
                }).collect(Collectors.toList());
        for(Purchase p : purchasesToDelete)
        {
            System.out.println(DateUtils.formatDate(now.getTime()) + " : Deleting purchase, timestamp : " + p.getTimestamp());
            purchaseRepository.delete(p);
        }

    }

    public Purchase getPurchaseByToken(String token) throws PaymentTimeoutException {
        Purchase purchase = purchaseRepository.findFirstByPaypalID(token);
        if(purchase == null) throw new PaymentTimeoutException("Time to make payment is out. Thanks for your money.");
        else return purchase;
    }

    public void setPurchasePaid(Purchase purchase)
    {
        purchase.setPaid(true);
        purchaseRepository.save(purchase);
    }

    public Purchase getPurchaseByPurchaseId(int id)
    {
        return purchaseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public List<Purchase> getAllByUserId(String id){
        return purchaseRepository.findAllByUserId(id);
    }
}
