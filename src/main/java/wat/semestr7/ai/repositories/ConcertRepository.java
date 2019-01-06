package wat.semestr7.ai.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr7.ai.dtos.ConcertDetailsDto;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.others.ConcertDetails;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ConcertRepository extends CrudRepository<Concert,Integer>
{
    List<Concert> findAll();

    @Query("select ticketCost from Concert where idConcert = :id")
    BigDecimal getConcertPriceByIdConcert(@Param("id") int idConcert);

    @Query(value = "select new wat.semestr7.ai.others.ConcertDetails(c.idConcert,c.concertTitle,c.date) from Concert c" +
            " where c.date < :now")
    List<ConcertDetails> getConcertDetailsList(@Param("now") Date beforeNow);
}
