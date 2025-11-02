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
                System.out.println("Loaded saved library state from " + SAVEFILE);
                return (LibraryHandler) obj;
            } else {
                System.err.println("Error: Saved file is corrupt or of an incompatible type.");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: Failed to load state. " + e.getMessage());
            return null;
        }
    }

    public void saveState(LibraryHandler handler) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVEFILE))) {
            oos.writeObject(handler);
            System.out.println("State successfully saved to " + SAVEFILE);
        } catch (IOException e) {
            System.err.println("Error: Failed to save state. " + e.getMessage());
        }
    }
}