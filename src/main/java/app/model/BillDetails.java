package app.model;

import javax.persistence.*;

@Entity
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne
    Bill bill;
    @ManyToOne
    Book book;
    int quantity;

    public BillDetails(Bill bill, Book book, int quantity) {
        this.bill = bill;
        this.book = book;
        this.quantity = quantity;
    }
}
