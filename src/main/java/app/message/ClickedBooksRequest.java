package app.message;

import javax.validation.constraints.NotNull;

public class ClickedBooksRequest {
    @NotNull
    Long bookId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
