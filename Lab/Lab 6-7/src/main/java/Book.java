import java.io.Serial;
import java.io.Serializable;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String title;
    private final String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book other = (Book) o;
        return title.equals(other.title) && author.equals(other.author);
    }

    @Override
    public int hashCode() {
        return 31 * title.hashCode() + author.hashCode();
    }

    @Override
    public String toString() {
        return "Book \"" + title + "\" by " + author;
    }
}