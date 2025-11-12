import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryHandler implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Catalogue catalogue;
    private final List<Record> records = new ArrayList<>();
    private final Administrator admin;
    private final Librarian librarian;
    private final List<Reader> readers = new ArrayList<>();
    private String lastLoggedInEmail = null;

    public LibraryHandler(Catalogue catalogue, Librarian librarian, Administrator admin) {
        this.catalogue = catalogue;
        this.librarian = librarian;
        this.admin = admin;
    }

    public void addReader(Reader reader) {
        if (reader.getEmail() != null && !reader.getEmail().isBlank()) {
            Reader existing = findReaderByEmail(reader.getEmail());
            if (existing != null) return;
        }
        readers.add(reader);
    }

    public Reader findReaderByEmail(String email) {
        if (email == null) return null;
        for (Reader r : readers) {
            String e = r.getEmail();
            if (e != null && e.equalsIgnoreCase(email)) return r;
        }
        return null;
    }

    public List<Reader> getReaders() { return readers; }
    public String getLastLoggedInEmail() { return lastLoggedInEmail; }
    public void setLastLoggedInEmail(String email) { this.lastLoggedInEmail = email; }
    public List<Record> getAllRecords() { return records; }
    public Catalogue getCatalogue() { return catalogue; }
    public List<Record> getRecordsByReader(Reader reader) {
        List<Record> res = new ArrayList<>();
        for (Record r : records) {
            if (r.getReader().equals(reader) && !r.isReturned()) res.add(r);
        }
        return res;
    }
    public Administrator getAdmin() { return admin; }

    public Record placeOrderByTitle(Reader reader, String title, boolean readingRoom) {
        if (admin.isBlacklisted(reader)) {
            throw new RuntimeException(Localizator.tr("errors.reader.blacklisted"));
        }
        Optional<Book> found = catalogue.searchByTitle(title);
        if (found.isEmpty()) {
            throw new RuntimeException(Localizator.tr("errors.book.not.found", title));
        }
        Book book = found.get();
        Record r = librarian.issueBook(catalogue, book, reader, readingRoom);
        records.add(r);
        return r;
    }

    public void returnBook(Record record) {
        if (!records.contains(record)) {
            throw new RuntimeException(Localizator.tr("errors.record.not.found"));
        }
        if (record.isReturned()) {
            System.out.println(Localizator.tr("record.returned.already"));
            return;
        }
        record.markReturned();
        catalogue.returnBook(record.getBook());
        System.out.println(Localizator.tr("record.returned.ok", record.getBook()));
    }

    public void checkOverduesAndBlacklist(LocalDate onDate) {
        for (Record r : records) {
            if (r.isOverdue(onDate) && !admin.isBlacklisted(r.getReader())) {
                admin.addToBlacklist(r.getReader());
            }
        }
    }

    public void printRecords() {
        System.out.println(Localizator.tr("records.title"));
        for (Record r : records) {
            System.out.println(r);
        }
    }
    public void printCatalogue() { System.out.println(catalogue); }
}