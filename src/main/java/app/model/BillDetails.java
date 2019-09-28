package app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne
    Bill bill;
    @ManyToOne
    Book book;
    int quantity;

    public BillDetails() {
    }

    public BillDetails(Bill bill, Book book, int quantity) {
        this.bill = bill;
        this.book = book;
        this.quantity = quantity;
    }

}
