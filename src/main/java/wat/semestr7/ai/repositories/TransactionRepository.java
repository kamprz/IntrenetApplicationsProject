package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.dtos.finance.TransactionConcertDetails;
import wat.semestr7.ai.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Integer>
{
    List<Transaction> findAll();

    @Query(value = "select distinct new wat.semestr7.ai.dtos.finance.TransactionConcertDetails(c.concertTitle,c.date) from Ticket t" +
            " left join t.concert c" +
            " left join t.purchase p" +
            " where p.idPurchase = :id")
    TransactionConcertDetails getTransactionConcertDetailsByPurchaseId(@Param("id") int id);

    @Query(value = "select sum(c.ticket_cost - (c.ticket_cost * d.percents / 100))" +
            " from ticket t" +
            " join discount d on d.discount_id = t.discount_id" +
            " join purchase p on p.id_purchase = t.id_purchase" +
            " join concert c on c.id_concert = t.id_concert" +
            " where p.id_purchase = :id"
            ,nativeQuery = true)
    Double getPurchaseCost(@Param("id") int id);

    @Query(value = "select amount_after_transaction from transaction order by date desc limit 1",
    nativeQuery = true)
    Double getCurrentCash();
}
