import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Book b1 = new Book("Dubel", "Igor Kolba");
        Book b2 = new Book("Zubel", "Oleg Zubovich");
        Book b3 = new Book("Profile", "Alexander Buslavskiy");
        Book b4 = new Book("Ebel", "Stepikh Lachik");
        Book b5 = new Book("Babel", "God Himself");
        HashMap<Book, Integer> books = new HashMap<Book, Integer>();
        books.put(b1, 0);
        books.put(b2, 10);
        books.put(b3, 3);
        books.put(b4, 8);
        books.put(b5, 19);
        Catalogue catalogue =  new Catalogue(books);
        System.out.println(catalogue);
    }
}
