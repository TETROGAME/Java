public class Book {
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
        if (!(o instanceof Book other)) return false;
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