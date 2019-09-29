package app.service;

import app.message.BookListOrder;
import app.message.BookOrder;
import app.model.*;
import app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;
    public List<Cart> getAllItemInCart(String username){
        List<Cart> res = cartRepository.findAllByUser(userRepository.findByUsername(username));
        return res;
    }
    public List<Cart> addItem(String username, BookOrder order){
        User user = userRepository.findByUsername(username);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setBook(bookRepository.findById(order.getId()).get());
        cart.setQuantity(order.getQuantity());
        cartRepository.save(cart);
        return getAllItemInCart(username);
    }
    public List<Cart> editItem(String username,Long id, int quantity){
        Cart cart = cartRepository.findById(id).get();
        cart.setQuantity(quantity);
        return getAllItemInCart(username);
    }
    public List<Cart> deleteItem(String username, Long cartId){
        cartRepository.deleteById(cartId);
        return getAllItemInCart(username);
    }
    public void deleteAll(String username){
        cartRepository.deleteAllByUser_Username(username);
    }
    @Transactional
    public Bill pay(String username){
        List<Cart> carts = getAllItemInCart(username);
        BookOrder[] bookOrders = new BookOrder[carts.size()];
        int index = 0;
        for(Cart cart: carts){
            BookOrder order = new BookOrder(cart.getBook().getId(),cart.getQuantity());
            bookOrders[index] = order;
            index++;
        }
        BookListOrder listOrder = new BookListOrder();
        listOrder.setBookOrders(bookOrders);
        Bill result = postOrder(username,listOrder);
        deleteAll(username);
        return result;

    }
    public Bill postOrder(String username, BookListOrder listOrder){
        User user = userRepository.findByUsername(username);
        Date date = new Date(System.currentTimeMillis());
        Bill result = billRepository.save(new Bill(user,date));
        for (BookOrder order: listOrder.getBookOrders()) {
            Book book = bookRepository.findById(order.getId()).get();
            int bookQuantity = order.getQuantity();
            billDetailsRepository.save(new BillDetails(result, book, bookQuantity));
        }
        return result;
    }
}
