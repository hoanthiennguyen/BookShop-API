package app.service;

import app.model.Book;
import app.model.User;
import app.repository.BillDetailsRepository;
import app.repository.BookRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;



    public List<String> getAllBookName(){
        return bookRepository.findAll().stream().map(
                book -> book.getProductName())
                .collect(Collectors.toList());
    }
    public List<Book> getBooksByCategory(String category, Pageable pageable){
        switch (category){
            case "topSales":
                return getTopSales(pageable);
            case "discount":
                return getTopDiscount(pageable);
        }
        return bookRepository.findBooksByCategoryAndIsDelete(category, false, pageable);
    }
    public List<Book> searchBookByName(String name){
        return bookRepository.findBooksByIsDeleteAndProductNameContains(false,name);
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id).get();
    }
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }
    public List<Book> getTopDiscount(Pageable pageable){
        return bookRepository.findByIsDeleteOrderByDiscountDesc(false,pageable);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
    public List<Book> getTopSales(Pageable pageable){
         return billDetailsRepository.getTopSales(pageable);


    }


}
