import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Administrator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final Set<Reader> blacklist = new HashSet<>();
    private final Date createdAt;

    public Administrator(String name) {
        this.name = name;
        this.createdAt = new Date();
    }

    public void addToBlacklist(Reader reader) {
        blacklist.add(reader);
        System.out.printf(Localizator.tr("blacklist.add", name, reader.getSurname(), reader.getName()) + "%n");
    }

    public boolean isBlacklisted(Reader reader) {
        return blacklist.contains(reader);
    }

    public void printBlacklist() {
        System.out.println(Localizator.tr("blacklist.title"));
        if (blacklist.isEmpty()) {
            System.out.println(" " + Localizator.tr("blacklist.none"));
            return;
        }
        blacklist.forEach(r -> System.out.println("- " + r.getSurname() + " " + r.getName()));
    }

    public Set<Reader> getBlacklist() {
        return blacklist;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    @Override
    public String toString() {
        return "Administrator{name='" + name + "', createdAt=" + Localizator.fmt(createdAt) + "}";
    }
}