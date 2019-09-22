package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
@Entity
public class Book {
    @Id
    protected Long id;
    protected String productName;
    protected int quantity;
    protected int inventory;
    protected Date updateDate;
    protected boolean isDelete;
    protected String status;
    protected String description;
    protected String author;
    protected String publishedBy;
    protected String providedBy;
    protected int rating;
    protected int price;


}
