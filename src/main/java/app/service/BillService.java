package app.service;

import app.message.BookListOrder;
import app.message.BookOrder;
import app.model.Bill;
import app.model.BillDetails;
import app.model.Book;
import app.model.User;
import app.repository.BillDetailsRepository;
import app.repository.BillRepository;
import app.repository.BookRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class BillService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;

    public void postOrder(String username, BookListOrder listOrder){
        User user = userRepository.findByUsername(username);
        Date date = new Date(System.currentTimeMillis());
        Bill result = billRepository.save(new Bill(user,date));
        for (BookOrder order: listOrder.getBookOrders()) {
            Book book = bookRepository.findById(order.getId()).get();
            int bookQuantity = order.getQuantity();
            billDetailsRepository.save(new BillDetails(result, book, bookQuantity));
        }
    }
}
