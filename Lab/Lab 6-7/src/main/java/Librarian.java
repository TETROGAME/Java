import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Librarian implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;

    public Librarian(String name) {
        this.name = name;
    }

    public Record issueBook(Catalogue catalogue, Book book, Reader reader, boolean readingRoom) {
        catalogue.extractBook(book);

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = readingRoom ? issueDate : issueDate.plusDays(14);

        Record record = new Record(book, reader, issueDate, dueDate, readingRoom);
        System.out.printf("Librarian %s issued %s to %s %s (readingRoom=%s), due: %s%n",
                name, book, reader.getSurname(), reader.getName(), readingRoom, dueDate);
        return record;
    }
}