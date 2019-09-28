package app.repository;

import app.model.BillDetails;
import org.springframework.data.repository.CrudRepository;

public interface BillDetailsRepository extends CrudRepository<BillDetails, Long> {
}
