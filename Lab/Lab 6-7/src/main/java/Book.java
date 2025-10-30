public class Book {
    private final String title;
    private final String author;
    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }


    @Override
    public String toString() {
        return "Book \"" + title + "\" by " + author;
    }
}
