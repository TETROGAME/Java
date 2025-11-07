import java.io.*;

public class StateConnector {
    private static final String SAVEFILE = "LibrarySavefile.bin";

    public LibraryHandler loadState() {
        File f = new File(SAVEFILE);
        if (!f.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof LibraryHandler) {
                System.out.println(I18n.tr("app.loaded", SAVEFILE));
                return (LibraryHandler) obj;
            } else {
                System.err.println(I18n.tr("app.save.type.error"));
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(I18n.tr("app.load.error", e.getMessage()));
            return null;
        }
    }

    public void saveState(LibraryHandler handler) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVEFILE))) {
            oos.writeObject(handler);
            System.out.println(I18n.tr("app.save.ok", SAVEFILE));
        } catch (IOException e) {
            System.err.println(I18n.tr("app.save.error", e.getMessage()));
        }
    }
}