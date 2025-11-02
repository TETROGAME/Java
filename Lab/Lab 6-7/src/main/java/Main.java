import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SAVEFILE = "LibrarySavefile.bin";

    public static Reader gatherInformation(Scanner scanner) {
        String name;
        String surname;
        String patronymic;
        String address;
        String email;
        String phone;
        while (true) {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            if (name.isBlank()) {
                System.err.println("Name cannot be empty.");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Surname: ");
            surname = scanner.nextLine().trim();
            if (surname.isBlank()) {
                System.err.println("Surname cannot be empty.");
            } else {
                break;
            }
        }
        System.out.print("Patronymic (or leave empty): ");
        patronymic = scanner.nextLine().trim();
        while (true) {
            System.out.print("Address: ");
            address = scanner.nextLine().trim();
            if (address.isBlank()) {
                System.err.println("Address cannot be empty.");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (email.isBlank()) {
                System.err.println("Email cannot be empty.");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Phone number: ");
            phone = scanner.nextLine().trim();
            if (phone.isBlank()) {
                System.err.println("Phone number cannot be empty.");
            } else {
                break;
            }
        }
        return new Reader(name, surname, patronymic, address, email, phone);
    }

    public static void main(String[] args) {
        LibraryHandler handler = loadState();

        if (handler == null) {
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
            handler = new LibraryHandler(catalogue, librarian, admin);
            System.out.println("Initialized new library state.");
        } else {
            System.out.println("Loaded saved library state.");
        }

        try (Scanner scanner = new Scanner(System.in)) {
            Reader current = null;
            String lastEmail = handler.getLastLoggedInEmail();
            if (lastEmail != null) {
                Reader last = handler.findReaderByEmail(lastEmail);
                if (last != null) {
                    current = last;
                    System.out.println("Automatically logged in as previously used reader: " + current.getName() + " " + current.getSurname());
                }
            }

            while (current == null) {
                System.out.println("\nNo user logged in.");
                System.out.println("1) Login as existing reader (by email)");
                System.out.println("2) Add new reader");
                System.out.println("3) Enter details for this session (not saved)");
                System.out.print("Choose an option: ");
                String startChoice = scanner.nextLine().trim();
                switch (startChoice) {
                    case "1":
                        System.out.print("Enter your registered email: ");
                        String email = scanner.nextLine().trim();
                        Reader found = handler.findReaderByEmail(email);
                        if (found != null) {
                            current = found;
                            handler.setLastLoggedInEmail(email);
                            System.out.println("Logged in as: " + current.getName() + " " + current.getSurname());
                        } else {
                            System.out.println("No reader with that email found.");
                        }
                        break;
                    case "2":
                        current = createAndRegisterReader(handler, scanner);
                        break;
                    case "3":
                        current = createTempReader(scanner);
                        System.out.println("Proceeding with temporary reader: " + current.getName() + " " + current.getSurname());
                        break;
                    default:
                        System.out.println("Unknown option.");
                }
            }

            boolean running = true;
            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1) Show catalogue");
                System.out.println("2) Borrow book by title");
                System.out.println("3) List my loans");
                System.out.println("4) Return a loan");
                System.out.println("5) Check overdues (simulate)");
                System.out.println("6) View blacklist");
                System.out.println("7) Exit and save");
                System.out.println("8) Add new reader / Switch user");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1":
                            handler.printCatalogue();
                            break;
                        case "2":
                            System.out.print("Enter book title to borrow: ");
                            String title = scanner.nextLine().trim();
                            System.out.print("Reading room? (y/n): ");
                            String rr = scanner.nextLine().trim().toLowerCase();
                            boolean readingRoom = rr.equals("y") || rr.equals("yes");
                            try {
                                Record r = handler.placeOrderByTitle(current, title, readingRoom);
                                System.out.println("Borrowed: " + r.getBook() + ", due: " + r.getDueDate());
                            } catch (RuntimeException ex) {
                                System.out.println("Failed to borrow: " + ex.getMessage());
                            }
                            break;
                        case "3":
                            List<Record> mine = handler.getRecordsByReader(current);
                            if (mine.isEmpty()) {
                                System.out.println("No active loans found for you.");
                            } else {
                                System.out.println("Your active loans:");
                                for (int i = 0; i < mine.size(); i++) {
                                    System.out.printf("[%d] %s%n", i + 1, mine.get(i));
                                }
                            }
                            break;
                        case "4":
                            List<Record> mine2 = handler.getRecordsByReader(current);
                            if (mine2.isEmpty()) {
                                System.out.println("No active loans to return.");
                                break;
                            }
                            System.out.println("Select a loan to return:");
                            for (int i = 0; i < mine2.size(); i++) {
                                System.out.printf("[%d] %s%n", i + 1, mine2.get(i));
                            }
                            System.out.print("Enter number: ");
                            String idxStr = scanner.nextLine().trim();
                            int idx = Integer.parseInt(idxStr) - 1;
                            if (idx < 0 || idx >= mine2.size()) {
                                System.out.println("Invalid selection.");
                            } else {
                                handler.returnBook(mine2.get(idx));
                            }
                            break;
                        case "5":
                            System.out.print("Simulate check for overdues how many days from now? ");
                            String daysStr = scanner.nextLine().trim();
                            int days = Integer.parseInt(daysStr);
                            handler.checkOverduesAndBlacklist(LocalDate.now().plusDays(days));
                            break;
                        case "6":
                            handler.getAdmin().printBlacklist();
                            break;
                        case "7":
                            running = false;
                            break;
                        case "8":
                            System.out.println("1) Add new reader");
                            System.out.println("2) Login as existing reader (by email)");
                            System.out.print("Choose: ");
                            String sub = scanner.nextLine().trim();
                            if ("1".equals(sub)) {
                                current = createAndRegisterReader(handler, scanner);
                            } else if ("2".equals(sub)) {
                                System.out.print("Enter email: ");
                                String email = scanner.nextLine().trim();
                                Reader found = handler.findReaderByEmail(email);
                                if (found != null) {
                                    current = found;
                                    handler.setLastLoggedInEmail(email);
                                    System.out.println("Switched to: " + current.getName() + " " + current.getSurname());
                                } else {
                                    System.out.println("No reader with that email found.");
                                }
                            } else {
                                System.out.println("Unknown option.");
                            }
                            break;
                        default:
                            System.out.println("Unknown option.");
                    }
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
        }
        saveState(handler);
        System.out.println("State saved to " + SAVEFILE + ". Goodbye!");
    }

    private static Reader createAndRegisterReader(LibraryHandler handler, Scanner scanner) {
        System.out.println("Enter new reader details:");
        Reader r = gatherInformation(scanner);
        handler.addReader(r);
        handler.setLastLoggedInEmail(r.getEmail());
        System.out.println("Registered and logged in as: " + r.getName() + " " + r.getSurname());
        return r;
    }

    private static Reader createTempReader(Scanner scanner) {
        System.out.println("Enter reader details for this session (not saved):");
        return gatherInformation(scanner);
    }

    private static LibraryHandler loadState() {
        File f = new File(SAVEFILE);
        if (!f.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof LibraryHandler) {
                return (LibraryHandler) obj;
            } else {
                System.out.println("Saved file does not contain a LibraryHandler.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Failed to load saved state: " + e.getMessage());
            return null;
        }
    }

    private static void saveState(LibraryHandler handler) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVEFILE))) {
            oos.writeObject(handler);
        } catch (IOException e) {
            System.out.println("Failed to save state: " + e.getMessage());
        }
    }
}