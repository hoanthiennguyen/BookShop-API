package app.repository;

import app.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByCategoryAndIsDelete(String category, boolean delete, Pageable pageable);
    List<Book> findByIsDeleteOrderByDiscountDesc(boolean delete, Pageable pageable);
    List<Book> findBooksByIsDeleteAndProductNameContains(boolean delete,String name);
}
