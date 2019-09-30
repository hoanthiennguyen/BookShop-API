package app.repository;

import app.model.CartItem;
import app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUser(User user);
    void deleteAllByUser_Username(String username);
}
