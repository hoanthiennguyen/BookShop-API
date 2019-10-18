package app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    String deliveryAddress;

    public Bill(User user, Date dateCreated) {
        this.user = user;
        this.dateCreated = dateCreated;
    }

    public Bill(User user, String deliveryAddress, Date dateCreated) {
        this.user = user;
        this.deliveryAddress = deliveryAddress;
        this.dateCreated = dateCreated;
    }

    Date dateCreated;

    public Bill() {
    }


}
