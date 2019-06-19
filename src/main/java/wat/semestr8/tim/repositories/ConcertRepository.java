package wat.semestr8.tim.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wat.semestr8.tim.dtos.ConcertDetailsDto;
import wat.semestr8.tim.entities.Concert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ConcertRepository extends CrudRepository<Concert,Integer>
{
    List<Concert> findAll();

    @Query("select ticketCost from Concert where idConcert = :id")
    BigDecimal getConcertPriceByIdConcert(@Param("id") int idConcert);

    @Query(value = "select new wat.semestr8.tim.dtos.ConcertDetailsWithDate(c.idConcert,c.concertTitle,c.date) from Concert c" +
            " where c.date < :now")
    List<ConcertDetailsDto> getConcertDetailsList(@Param("now") Date beforeNow);
}
