package app.repository;

import app.model.Book;
import app.model.Cart;
import app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);
    void deleteAllByUser_Username(String username);
}
