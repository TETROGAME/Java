import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Record implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Book book;
    private final Reader reader;
    private final LocalDate issueDate;
    private LocalDate dueDate;
    private boolean returned;
    private final boolean readingRoom;

    public Record(Book book, Reader reader, LocalDate issueDate, LocalDate dueDate, boolean readingRoom) {
        this.id = UUID.randomUUID();
        this.book = book;
        this.reader = reader;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.readingRoom = readingRoom;
        this.returned = false;
    }

    public UUID getId() { return id; }
    public Book getBook() { return book; }
    public Reader getReader() { return reader; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isReturned() { return returned; }
    public boolean isReadingRoom() { return readingRoom; }

    public void markReturned() {
        this.returned = true;
    }

    public boolean isOverdue(LocalDate onDate) {
        if (returned) return false;
        if (dueDate == null) return false;
        return onDate.isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", book=" + book +
                ", reader=" + reader.getSurname() + " " + reader.getName() +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", readingRoom=" + readingRoom +
                ", returned=" + returned +
                '}';
    }
}