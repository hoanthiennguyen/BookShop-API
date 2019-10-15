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
    public List<CartItem> getAllItemInCart(String username){
        List<CartItem> res = cartRepository.findAllByUser(userRepository.findByUsername(username));
        return res;
    }
    public List<CartItem> addItem(String username, BookOrder order){
        User user = userRepository.findByUsername(username);
        Book book = bookRepository.findById(order.getId()).get();
        user.getClickedBooks().remove(book);
        List<CartItem> listCartItems = cartRepository.findAllByUser(user);
        boolean alreadyInCart = false;
        for(CartItem cartItem: listCartItems){
            if(cartItem.getBook().getId()== order.getId()){
                cartItem.setQuantity(cartItem.getQuantity()+ order.getQuantity());
                cartRepository.save(cartItem);
                alreadyInCart = true;
                break;
            }
        }
        if(!alreadyInCart){
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);

            cartItem.setBook(book);
            cartItem.setQuantity(order.getQuantity());
            cartRepository.save(cartItem);
        }

        return getAllItemInCart(username);
    }

    public List<CartItem> editItem(String username, Long id, int quantity){
        CartItem cartItem = cartRepository.findById(id).get();
        cartItem.setQuantity(quantity);
        cartRepository.save(cartItem);
        return getAllItemInCart(username);
    }
    public List<CartItem> deleteItem(String username, Long cartId){
        cartRepository.deleteById(cartId);
        return getAllItemInCart(username);
    }
    public void deleteAll(String username){
        cartRepository.deleteAllByUser_Username(username);
    }
    @Transactional
    public Bill pay(String username){
        List<CartItem> cartItems = getAllItemInCart(username);
        BookOrder[] bookOrders = new BookOrder[cartItems.size()];
        int index = 0;
        //prepare order
        for(CartItem cartItem : cartItems){

            //subtract number of quantity left
            Book book = cartItem.getBook();
            book.setQuantity(book.getQuantity() - cartItem.getQuantity());
            bookRepository.save(book);

            BookOrder order = new BookOrder(cartItem.getBook().getId(), cartItem.getQuantity());
            bookOrders[index] = order;
            index++;
        }

        BookListOrder listOrder = new BookListOrder();
        listOrder.setBookOrders(bookOrders);
        //save bill
        Bill result = postOrder(username,listOrder);

        //delete all item in card
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
