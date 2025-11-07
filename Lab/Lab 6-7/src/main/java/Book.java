import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String title;
    private final String author;
    private final Date createdAt;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.createdAt = new Date();
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Date getCreatedAt() { return new Date(createdAt.getTime()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book other)) return false;
        return title.equals(other.title) && author.equals(other.author);
    }

    @Override
    public int hashCode() {
        return 31 * title.hashCode() + author.hashCode();
    }

    @Override
    public String toString() {
        return "Book \"" + title + "\" by " + author + " (createdAt=" + I18n.fmt(createdAt) + ")";
    }
}