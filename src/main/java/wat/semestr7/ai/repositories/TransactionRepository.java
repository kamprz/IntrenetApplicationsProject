package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Integer>
{
    List<Transaction> findAll();
/*
    @Query(value = "select c.concertTitle from ticket t" +
            " left join t.concert c" +
            " left join t.purchase p" +
            " where p.idPurchase = :id")
    String getConcertTitleByPurchaseId(@Param("id") int id);
*/
    /*@Query(value = "select sum(c.ticketCost - (c.ticketCost * d.percents / 100)) from ticket t" +
            " left join t.discount d" +
            " left join t.purchase p" +
            " left join concert c" +
            " where p.idPurchase = :id")
    BigDecimal getPurchaseCost(@Param("id") int id);*/
}
