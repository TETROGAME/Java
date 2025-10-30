import java.time.LocalDate;

public class Librarian {
    private final String name;

    public Librarian(String name) {
        this.name = name;
    }

    public Record issueBook(Catalogue catalogue, Book book, Reader reader, boolean readingRoom) {
        try{
            catalogue.extractBook(book);
        } catch(RuntimeException e){
            System.err.println(e.getMessage());
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = readingRoom ? issueDate : issueDate.plusDays(14);

        Record record = new Record(book, reader, issueDate, dueDate, readingRoom);
        System.out.printf("Librarian %s issued %s to %s %s (readingRoom=%s), due: %s%n",
                name, book, reader.getSurname(), reader.getName(), readingRoom, dueDate);
        return record;
    }
}