package app.repository;

import app.model.BillDetails;
import app.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillDetailsRepository extends CrudRepository<BillDetails, Long> {
    @Query("SELECT bill.book, SUM(bill.quantity) as sumquantity\n" +
            " FROM BillDetails bill\n" +
            " GROUP BY bill.book\n" +
            " ORDER BY sumquantity DESC")
    List<Book> getTop10Sales();
}
