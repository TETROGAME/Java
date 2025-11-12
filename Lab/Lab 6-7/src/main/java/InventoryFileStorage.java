import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class InventoryFileStorage {
    private static final Path BOOKS_FILE = Paths.get("books.csv");

    private InventoryFileStorage() {}

    public static HashMap<Book, Integer> loadCatalogueFromFile() {
        if (!Files.exists(BOOKS_FILE)) {
            return new HashMap<>();
        }
        try (BufferedReader br = Files.newBufferedReader(BOOKS_FILE, StandardCharsets.UTF_8)) {
            String header = br.readLine();
            HashMap<Book, Integer> map = new HashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = parseCsvLine(line);
                if (cols == null || cols.length != 3) continue;
                String title = unquote(cols[0]);
                String author = unquote(cols[1]);
                String countStr = unquote(cols[2]);
                int count = 0;
                try {
                    count = Integer.parseInt(countStr.trim());
                } catch (NumberFormatException ignored) {}
                map.merge(new Book(title, author), count, Integer::sum);
            }
            return map;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void saveCatalogueToFile(Catalogue catalogue) {
        HashMap<Book, Integer> snapshot = catalogue.getBooksSnapshot();
        try (BufferedWriter bw = Files.newBufferedWriter(BOOKS_FILE, StandardCharsets.UTF_8)) {
            bw.write("title,author,count");
            bw.newLine();
            for (var entry : snapshot.entrySet()) {
                Book b = entry.getKey();
                int count = entry.getValue();
                bw.write(quote(b.getTitle()));
                bw.write(",");
                bw.write(quote(b.getAuthor()));
                bw.write(",");
                bw.write(quote(Integer.toString(count)));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String quote(String s) {
        if (s == null) return "\"\"";
        String escaped = s.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private static String unquote(String s) {
        if (s == null) return "";
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            String inner = s.substring(1, s.length() - 1);
            return inner.replace("\"\"", "\"");
        }
        return s;
    }

    private static String[] parseCsvLine(String line) {
        if (line == null) return null;
        List<String> parts = new ArrayList<>(3);
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                parts.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        parts.add(sb.toString());
        return parts.toArray(new String[0]);
    }
}