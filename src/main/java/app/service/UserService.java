package app.service;

 
import javax.transaction.Transactional;

import app.model.Book;
import app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.model.User;
import app.repository.UserRepository;

import java.util.List;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        
        User user = userRepository.findByUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }
    
    
    public User updateProfile(User user) {

    	return userRepository.save(user);
    	
    }
    
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    public List<Book> getClickedBooks(String username){
        User user = userRepository.findByUsername(username);
        return user.getClickedBooks();
    }
    public List<Book> addBookToListOfClickedBooks(String username, Long bookId){
        User user = userRepository.findByUsername(username);
        Book book = bookRepository.findById(bookId).get();
        List<Book> books = user.getClickedBooks();
        if(!books.contains(book))
            books.add(book);
        else {
            books.remove(book);
            books.add(0,book);
        }
        userRepository.save(user);
        return user.getClickedBooks();
    }
    public void deleteClickedBook(String username, Long bookId){
        User user = userRepository.findByUsername(username);
        Book book = bookRepository.findById(bookId).get();
        List<Book> books = user.getClickedBooks();
        books.remove(book);
    }



}