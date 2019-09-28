package app.repository;

import app.model.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository  extends CrudRepository<Bill, Long> {

}
