import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Administrator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final Set<Reader> blacklist = new HashSet<>();

    public Administrator(String name) {
        this.name = name;
    }

    public void addToBlacklist(Reader reader) {
        blacklist.add(reader);
        System.out.printf("Administrator %s added %s %s to blacklist%n", name, reader.getSurname(), reader.getName());
    }

    public boolean isBlacklisted(Reader reader) {
        return blacklist.contains(reader);
    }

    public void printBlacklist() {
        System.out.println("Blacklist:");
        if (blacklist.isEmpty()) {
            System.out.println(" (none)");
            return;
        }
        blacklist.forEach(r -> System.out.println("- " + r.getSurname() + " " + r.getName()));
    }

    public Set<Reader> getBlacklist() {
        return blacklist;
    }
}