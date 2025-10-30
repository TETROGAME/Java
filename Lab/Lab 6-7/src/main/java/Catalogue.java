import java.util.ArrayList;
import java.util.HashMap;

public class Catalogue {
    private HashMap<Book, Integer> books;
    public Catalogue(HashMap<Book, Integer> books) {
        this.books = books;
    }


    public void extractBook(Book book) {
        if(books.containsKey(book)) {
            if(books.get(book) > 0) {
                books.replace(book, books.get(book) - 1);
            }
            else throw new  RuntimeException("This book is out of stock!");
        }
        else {
            throw new RuntimeException("Book not found");
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Books available:\n");
        int max_line_length = -1;
        for(Book book : books.keySet()) {
            if(max_line_length < book.toString().length()) {
                max_line_length = book.toString().length();
            }
        }

        sb.append("-".repeat(Math.max(0, max_line_length))).append('\n');
        for(var book : books.entrySet()) {
            sb
                    .append(book.getKey().toString())
                    .append(", available: ")
                    .append(book.getValue().toString())
                    .append("\n");
        }
        sb.append("-".repeat(Math.max(0, max_line_length))).append('\n');

        return sb.toString();
    }
}
