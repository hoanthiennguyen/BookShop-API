package app.message;

import javax.validation.constraints.NotEmpty;

public class BookListOrder {
    @NotEmpty
    BookOrder[] bookOrders;

    public BookOrder[] getBookOrders() {
        return bookOrders;
    }

    public void setBookOrders(BookOrder[] bookOrders) {
        this.bookOrders = bookOrders;
    }
}

