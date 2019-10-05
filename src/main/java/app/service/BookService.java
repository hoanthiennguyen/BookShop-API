package app.service;

import app.model.Book;
import app.model.User;
import app.repository.BillDetailsRepository;
import app.repository.BookRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;

    public List<Book> addBookToListOfClickedBooks(String username, Long bookId){
        User user = userRepository.findByUsername(username);
        Book book = bookRepository.findById(bookId).get();
        List<Book> books = user.getClickedBooks();
        if(!books.contains(book))
            books.add(book);
        else {
            books.remove(book);
            books.add(book);
        }
        userRepository.save(user);
        return user.getClickedBooks();
    }
    public List<Book> getAllClickBooks(String username){
        User user = userRepository.findByUsername(username);
        return user.getClickedBooks();
    }
    public List<Book> getBooksByCategory(String category){
        return bookRepository.findBooksByCategoryAndIsDelete(category, false);
    }
    public List<Book> searchBookByName(String name){
        return bookRepository.findBooksByProductNameContains(name);
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id).get();
    }
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }
    public List<Book> getTop10Discount(){
        return bookRepository.findTop10ByOrderByDiscountDesc();
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
    public List<Book> getTopSales(){
        List<Book> result = new ArrayList<>();
        int i = 0;
        List<Book> all = billDetailsRepository.getTop10Sales();
        for (Book book: all) {
            if(i <10){
                result.add(book);
                i++;
            }
        }
         return result;
    }


}
