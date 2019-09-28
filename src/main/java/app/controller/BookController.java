package app.controller;

import app.message.BaseResponse;
import app.message.ClickedBooksResponse;
import app.message.ClickedBooksRequest;
import app.model.Book;
import app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }
    @GetMapping("/clicked-books")
    public BaseResponse getAllClickedBooks(@AuthenticationPrincipal Authentication authenticationPrincipal){
        List<Book> books = bookService.getAllClickBooks(authenticationPrincipal.getName());
        BaseResponse response = new BaseResponse();
        response.setData(books);
        return response;
    }
    @PostMapping("/clicked-books")
    public BaseResponse addBookOnListOfClickedBook(@AuthenticationPrincipal Authentication authentication,
                                                   @Valid @RequestBody ClickedBooksRequest clickedBooksRequest){
        Long bookId = clickedBooksRequest.getBookId();
        String username = authentication.getName();
        bookService.addBookToListOfClickedBooks(username,bookId);
        ClickedBooksResponse res = new ClickedBooksResponse();
        return res;
    }

    @GetMapping("/top10-discount")
    public BaseResponse findTop10Discount(){
        BaseResponse response = new BaseResponse();
        response.setData(new ArrayList<>());
        return response;
    }


}
