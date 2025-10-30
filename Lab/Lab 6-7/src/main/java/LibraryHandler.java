import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryHandler {
    private final Catalogue catalogue;
    private final List<Record> records = new ArrayList<>();
    private final Administrator admin;
    private final Librarian librarian;

    public LibraryHandler(Catalogue catalogue, Librarian librarian, Administrator admin) {
        this.catalogue = catalogue;
        this.librarian = librarian;
        this.admin = admin;
    }

    public Record placeOrderByTitle(Reader reader, String title, boolean readingRoom) {
        if (admin.isBlacklisted(reader)) {
            throw new RuntimeException("Reader is blacklisted and cannot borrow books");
        }
        Optional<Book> found = catalogue.searchByTitle(title);
        if (found.isEmpty()) {
            throw new RuntimeException("Book with title '" + title + "' not found in catalogue");
        }
        Book book = found.get();
        Record r = librarian.issueBook(catalogue, book, reader, readingRoom);
        records.add(r);
        return r;
    }

    public void returnBook(Record record) {
        if (!records.contains(record)) {
            throw new RuntimeException("Record not found");
        }
        if (record.isReturned()) {
            System.out.println("Book already returned.");
            return;
        }
        record.markReturned();
        catalogue.returnBook(record.getBook());
        System.out.println("Book returned: " + record.getBook());
    }

    public void checkOverduesAndBlacklist(LocalDate onDate) {
        for (Record r : records) {
            if (r.isOverdue(onDate) && !admin.isBlacklisted(r.getReader())) {
                admin.addToBlacklist(r.getReader());
            }
        }
    }

    public void printRecords() {
        System.out.println("Records:");
        for (Record r : records) {
            System.out.println(r);
        }
    }

    public void printCatalogue() {
        System.out.println(catalogue);
    }
}