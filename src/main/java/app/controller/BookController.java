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
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;



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


    @PostMapping
    public BaseResponse saveBook(@RequestBody Book book){
        BaseResponse response = new BaseResponse();
        response.setData(bookService.saveBook(book));
        return response;
    }
    @PutMapping("/{id}")
    public BaseResponse updateBook(@RequestBody Book book){
        BaseResponse response = new BaseResponse();
        response.setData(bookService.saveBook(book));
        return response;
    }

    @GetMapping
    public BaseResponse getBookByCategory(@RequestParam String category){
        BaseResponse response = new BaseResponse();
        response.setData(bookService.getBooksByCategory(category));
        return response;
    }
    @GetMapping("/discount")
    public BaseResponse getTop10Discount(){
        BaseResponse response = new BaseResponse();
        response.setData(bookService.getTop10Discount());
        return response;
    }
    @DeleteMapping("/{id}")
    public BaseResponse deleteBook(@PathVariable Long id){
        BaseResponse response = new BaseResponse();
        bookService.deleteBook(id);
        return response;
    }


}
