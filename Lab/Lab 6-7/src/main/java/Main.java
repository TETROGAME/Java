import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static Reader gatherInformation(Scanner scanner) {
        String name;
        String surname;
        String patronymic;
        String address;
        String email;
        String phone;
        while (true) {
            System.out.print(I18n.tr("reader.prompt.name") + " ");
            name = scanner.nextLine().trim();
            if (name.isBlank()) {
                System.err.println(I18n.tr("reader.error.empty", I18n.tr("reader.prompt.name")));
            } else {
                break;
            }
        }
        while (true) {
            System.out.print(I18n.tr("reader.prompt.surname") + " ");
            surname = scanner.nextLine().trim();
            if (surname.isBlank()) {
                System.err.println(I18n.tr("reader.error.empty", I18n.tr("reader.prompt.surname")));
            } else {
                break;
            }
        }
        System.out.print(I18n.tr("reader.prompt.patronymic") + " ");
        patronymic = scanner.nextLine().trim();
        while (true) {
            System.out.print(I18n.tr("reader.prompt.address") + " ");
            address = scanner.nextLine().trim();
            if (address.isBlank()) {
                System.err.println(I18n.tr("reader.error.empty", I18n.tr("reader.prompt.address")));
            } else {
                break;
            }
        }
        while (true) {
            System.out.print(I18n.tr("reader.prompt.email") + " ");
            email = scanner.nextLine().trim();
            if (email.isBlank()) {
                System.err.println(I18n.tr("reader.error.empty", I18n.tr("reader.prompt.email")));
            } else {
                break;
            }
        }
        while (true) {
            System.out.print(I18n.tr("reader.prompt.phone") + " ");
            phone = scanner.nextLine().trim();
            if (phone.isBlank()) {
                System.err.println(I18n.tr("reader.error.empty", I18n.tr("reader.prompt.phone")));
            } else {
                break;
            }
        }
        return new Reader(name, surname, patronymic, address, email, phone);
    }

    public static void main(String[] args) {
        Locale locale = (args != null && args.length >= 2) ? new Locale(args[0], args[1]) : Locale.UK;
        I18n.init(locale);

        StateConnector connector = new StateConnector();
        LibraryHandler handler = connector.loadState();

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
            System.out.println(I18n.tr("app.init.new"));
        }

        try (Scanner scanner = new Scanner(System.in)) {
            Reader current = null;
            String lastEmail = handler.getLastLoggedInEmail();
            if (lastEmail != null) {
                Reader last = handler.findReaderByEmail(lastEmail);
                if (last != null) {
                    current = last;
                    current.incrementLoginCount();
                    System.out.println(I18n.tr("login.auto", current.getName(), current.getSurname()));
                    System.out.println(I18n.tr("login.count", current.getSessionLoginCount()));
                }
            }

            while (current == null) {
                System.out.println("\n" + I18n.tr("menu.user.none"));
                System.out.println(I18n.tr("login.options.1"));
                System.out.println(I18n.tr("login.options.2"));
                System.out.println(I18n.tr("login.options.3"));
                System.out.print(I18n.tr("menu.choice") + " ");
                String startChoice = scanner.nextLine().trim();
                switch (startChoice) {
                    case "1":
                        System.out.print(I18n.tr("login.prompt.email") + " ");
                        String email = scanner.nextLine().trim();
                        Reader found = handler.findReaderByEmail(email);
                        if (found != null) {
                            current = found;
                            current.incrementLoginCount();
                            handler.setLastLoggedInEmail(email);
                            System.out.println(I18n.tr("login.logged", current.getName(), current.getSurname()));
                        } else {
                            System.out.println(I18n.tr("login.notfound"));
                        }
                        break;
                    case "2":
                        current = createAndRegisterReader(handler, scanner);
                        break;
                    case "3":
                        current = createTempReader(scanner);
                        System.out.println(I18n.tr("temp.proceed", current.getName(), current.getSurname()));
                        break;
                    default:
                        System.out.println(I18n.tr("menu.unknown"));
                }
            }

            boolean running = true;
            while (running) {
                System.out.println("\n" + I18n.tr("menu"));
                System.out.println(I18n.tr("menu.1"));
                System.out.println(I18n.tr("menu.2"));
                System.out.println(I18n.tr("menu.3"));
                System.out.println(I18n.tr("menu.4"));
                System.out.println(I18n.tr("menu.5"));
                System.out.println(I18n.tr("menu.6"));
                System.out.println(I18n.tr("menu.7"));
                System.out.println(I18n.tr("menu.8"));
                System.out.print(I18n.tr("menu.choice") + " ");
                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1":
                            handler.printCatalogue();
                            break;
                        case "2":
                            System.out.print(I18n.tr("borrow.prompt.title") + " ");
                            String title = scanner.nextLine().trim();
                            System.out.print(I18n.tr("borrow.prompt.rr") + " ");
                            String rr = scanner.nextLine().trim();
                            boolean readingRoom = I18n.isYes(rr);
                            try {
                                Record r = handler.placeOrderByTitle(current, title, readingRoom);
                                System.out.println(I18n.tr("borrow.ok", r.getBook(), I18n.fmt(r.getDueDate())));
                            } catch (RuntimeException ex) {
                                System.out.println(I18n.tr("borrow.fail", ex.getMessage()));
                            }
                            break;
                        case "3":
                            List<Record> mine = handler.getRecordsByReader(current);
                            if (mine.isEmpty()) {
                                System.out.println(I18n.tr("loans.none"));
                            } else {
                                System.out.println(I18n.tr("loans.title"));
                                for (int i = 0; i < mine.size(); i++) {
                                    System.out.printf("[%d] %s%n", i + 1, mine.get(i));
                                }
                            }
                            break;
                        case "4":
                            List<Record> mine2 = handler.getRecordsByReader(current);
                            if (mine2.isEmpty()) {
                                System.out.println(I18n.tr("loans.return.none"));
                                break;
                            }
                            System.out.println(I18n.tr("loans.return.select"));
                            for (int i = 0; i < mine2.size(); i++) {
                                System.out.printf("[%d] %s%n", i + 1, mine2.get(i));
                            }
                            System.out.print(I18n.tr("loans.return.enter") + " ");
                            String idxStr = scanner.nextLine().trim();
                            int idx = Integer.parseInt(idxStr) - 1;
                            if (idx < 0 || idx >= mine2.size()) {
                                System.out.println(I18n.tr("loans.return.invalid"));
                            } else {
                                handler.returnBook(mine2.get(idx));
                            }
                            break;
                        case "5":
                            System.out.print(I18n.tr("overdue.simulate") + " ");
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
                            System.out.println(I18n.tr("switch.title"));
                            System.out.println(I18n.tr("switch.title2"));
                            System.out.print(I18n.tr("switch.choose") + " ");
                            String sub = scanner.nextLine().trim();
                            if ("1".equals(sub)) {
                                current = createAndRegisterReader(handler, scanner);
                            } else if ("2".equals(sub)) {
                                System.out.print(I18n.tr("reader.prompt.email") + " ");
                                String email = scanner.nextLine().trim();
                                Reader found = handler.findReaderByEmail(email);
                                if (found != null) {
                                    current = found;
                                    current.incrementLoginCount();
                                    handler.setLastLoggedInEmail(email);
                                    System.out.println(I18n.tr("switch.switched", current.getName(), current.getSurname()));
                                } else {
                                    System.out.println(I18n.tr("login.notfound"));
                                }
                            } else {
                                System.out.println(I18n.tr("menu.unknown"));
                            }
                            break;
                        default:
                            System.out.println(I18n.tr("menu.unknown"));
                    }
                } catch (Exception ex) {
                    System.err.println(I18n.tr("error.generic", ex.getMessage()));
                }
            }
        }
        connector.saveState(handler);
    }

    private static Reader createAndRegisterReader(LibraryHandler handler, Scanner scanner) {
        System.out.println(I18n.tr("register.enter"));
        Reader r = gatherInformation(scanner);
        handler.addReader(r);
        handler.setLastLoggedInEmail(r.getEmail());
        System.out.println(I18n.tr("register.ok", r.getName(), r.getSurname()));
        return r;
    }

    private static Reader createTempReader(Scanner scanner) {
        System.out.println(I18n.tr("register.enter"));
        return gatherInformation(scanner);
    }
}