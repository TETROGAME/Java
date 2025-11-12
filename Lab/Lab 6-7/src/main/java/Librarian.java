import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Librarian implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final Date createdAt;

    public Librarian(String name) {
        this.name = name;
        this.createdAt = new Date();
    }

    public Record issueBook(Catalogue catalogue, Book book, Reader reader, boolean readingRoom) {
        catalogue.extractBook(book);

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = readingRoom ? issueDate : issueDate.plusDays(14);

        Record record = new Record(book, reader, issueDate, dueDate, readingRoom);
        System.out.printf(
                Localizator.tr("librarian.issued", name, book, reader.getSurname(), reader.getName(),
                        readingRoom, Localizator.fmt(dueDate)) + "%n");
        return record;
    }

    public Date getCreatedAt() { return new Date(createdAt.getTime()); }

    @Override
    public String toString() {
        return "Librarian{name='" + name + "', createdAt=" + Localizator.fmt(createdAt) + "}";
    }
}