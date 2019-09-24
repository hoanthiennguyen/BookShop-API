package app.userservice;

import app.model.Book;
import app.model.User;
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
    public List<Book> getAllBooks(){
        List<Book> result = new ArrayList<>();
          bookRepository.findAll().forEach(book -> {
          result.add(book);
        });
          return result;
    }
    public List<Book> addAnotherBooks(String username, Long bookId){
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
}
