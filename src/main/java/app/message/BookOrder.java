package app.message;

import javax.validation.constraints.NotNull;

public class BookOrder {
    @NotNull
    Long id;
    @NotNull
    int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
