package app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

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

    public Bill(User user, Date dateCreated) {
        this.user = user;
        this.dateCreated = dateCreated;
    }

    Date dateCreated;

    public Bill() {
    }


}
