import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class Catalogue implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final HashMap<Book, Integer> books;
    private final Date createdAt;

    public Catalogue(HashMap<Book, Integer> books) {
        this.books = books;
        this.createdAt = new Date();
    }

    public Optional<Book> searchByTitle(String title) {
        return books.keySet().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public Optional<Book> searchByAuthor(String author) {
        return books.keySet().stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .findFirst();
    }

    public void extractBook(Book book) {
        if (books.containsKey(book)) {
            if (books.get(book) > 0) {
                books.replace(book, books.get(book) - 1);
            } else throw new RuntimeException(I18n.tr("errors.out.of.stock"));
        } else {
            throw new RuntimeException(I18n.tr("errors.book.not.found", book.getTitle()));
        }
    }

    public void returnBook(Book book) {
        books.merge(book, 1, Integer::sum);
    }

    public void addBook(Book book, int count) {
        books.merge(book, count, Integer::sum);
    }

    public int availableCopies(Book book) {
        return books.getOrDefault(book, 0);
    }

    public Date getCreatedAt() { return new Date(createdAt.getTime()); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(I18n.tr("catalogue.title")).append('\n');
        int max_line_length = -1;
        for (Book book : books.keySet()) {
            if (max_line_length < book.toString().length()) {
                max_line_length = book.toString().length();
            }
        }
        sb.append("-".repeat(Math.max(0, max_line_length))).append('\n');
        for (var book : books.entrySet()) {
            sb.append(book.getKey().toString())
                    .append(", ")
                    .append(I18n.tr("catalogue.available", book.getValue().toString()))
                    .append("\n");
        }
        sb.append("-".repeat(Math.max(0, max_line_length))).append('\n');
        sb.append("Catalogue createdAt: ").append(I18n.fmt(createdAt)).append('\n');
        return sb.toString();
    }
}