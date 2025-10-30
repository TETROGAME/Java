import java.time.LocalDate;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Book b1 = new Book("Dubel", "Igor Kolba");
        Book b2 = new Book("Zubel", "Oleg Zubovich");
        Book b3 = new Book("Profile", "Alexander Buslavskiy");
        Book b4 = new Book("Ebel", "Stepikh Lachik");
        Book b5 = new Book("Babel", "God Himself");
        HashMap<Book, Integer> books = new HashMap<>();
        books.put(b1, 0);
        books.put(b2, 10);
        books.put(b3, 3);
        books.put(b4, 8);
        books.put(b5, 19);
        Catalogue catalogue = new Catalogue(books);

        Librarian librarian = new Librarian("Anna");
        Administrator admin = new Administrator("Ivan");

        LibraryHandler handler = new LibraryHandler(catalogue, librarian, admin);

        Reader alice = new Reader("Alice", "Smith", "A.", "123 Main St", "alice@example.com", "555-0001");
        Reader bob = new Reader("Bob", "Jones", "B.", "456 Oak Ave", "bob@example.com", "555-0002");

        handler.printCatalogue();

        Record rec1 = handler.placeOrderByTitle(alice, "Profile", false);

        try {
            handler.placeOrderByTitle(bob, "Dubel", false);
        } catch (RuntimeException ex) {
            System.out.println("Failed to place order for Bob: " + ex.getMessage());
        }

        handler.printRecords();

        LocalDate future = LocalDate.now().plusDays(30);
        handler.checkOverduesAndBlacklist(future);

        admin.printBlacklist();

        handler.returnBook(rec1);

        // After return, Alice should not be blacklisted (if she was)
        System.out.println("Is Alice blacklisted? " + admin.isBlacklisted(alice));

        // final catalogue
        handler.printCatalogue();
    }
}