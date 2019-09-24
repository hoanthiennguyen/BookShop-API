package app.controller;

import app.message.ClickBookRequest;
import app.model.Book;
import app.userservice.BookService;
import app.userservice.CustomUserDetails;
import app.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/clicked_books")
    public List<Book> getAllClickedBooks(@AuthenticationPrincipal Authentication authenticationPrincipal){
        return bookService.getAllClickBooks(authenticationPrincipal.getName());
    }
    @PostMapping("/clicked_books")
    public List<Book> addBookOnListOfClickedBook(@AuthenticationPrincipal Authentication authentication,
                                                 @Valid @RequestBody ClickBookRequest clickBookRequest){
        Long bookId = clickBookRequest.getBookId();
        String username = authentication.getName();
        return bookService.addAnotherBooks(username,bookId);
    }


}
