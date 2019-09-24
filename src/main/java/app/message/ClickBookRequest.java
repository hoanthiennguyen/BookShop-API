package app.message;

import javax.validation.constraints.NotNull;

public class ClickBookRequest {
    @NotNull
    Long bookId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
