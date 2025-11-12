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
            System.out.print(Localizator.tr("reader.prompt.name") + " ");
            name = scanner.nextLine().trim();
            if (name.isBlank()) {
                System.err.println(Localizator.tr("reader.error.empty", Localizator.tr("reader.prompt.name")));
            } else {
                break;
            }
        }
        while (true) {
            System.out.print(Localizator.tr("reader.prompt.surname") + " ");
            surname = scanner.nextLine().trim();
            if (surname.isBlank()) {
                System.err.println(Localizator.tr("reader.error.empty", Localizator.tr("reader.prompt.surname")));
            } else {
                break;
            }
        }
        System.out.print(Localizator.tr("reader.prompt.patronymic") + " ");
        patronymic = scanner.nextLine().trim();
        while (true) {
            System.out.print(Localizator.tr("reader.prompt.address") + " ");
            address = scanner.nextLine().trim();
            if (address.isBlank()) {
                System.err.println(Localizator.tr("reader.error.empty", Localizator.tr("reader.prompt.address")));
            } else {
                break;
            }
        }
        while (true) {
            System.out.print(Localizator.tr("reader.prompt.email") + " ");
            email = scanner.nextLine().trim();
            if (email.isBlank()) {
                System.err.println(Localizator.tr("reader.error.empty", Localizator.tr("reader.prompt.email")));
            } else {
                break;
            }
        }
        while (true) {
            System.out.print(Localizator.tr("reader.prompt.phone") + " ");
            phone = scanner.nextLine().trim();
            if (phone.isBlank()) {
                System.err.println(Localizator.tr("reader.error.empty", Localizator.tr("reader.prompt.phone")));
            } else {
                break;
            }
        }
        return new Reader(name, surname, patronymic, address, email, phone);
    }

    public static void main(String[] args) {
        Locale locale = (args != null && args.length >= 2) ? new Locale(args[0], args[1]) : Locale.UK;
        Localizator.init(locale);

        StateConnector connector = new StateConnector();
        LibraryHandler handler = connector.loadState();

        if (handler == null) {
            HashMap<Book, Integer> books = InventoryFileStorage.loadCatalogueFromFile();
            Catalogue catalogue = new Catalogue(books);
            Librarian librarian = new Librarian("Anna");
            Administrator admin = new Administrator("Ivan");
            handler = new LibraryHandler(catalogue, librarian, admin);
            System.out.println(Localizator.tr("app.init.new"));
        }

        try (Scanner scanner = new Scanner(System.in)) {
            Reader current = null;
            String lastEmail = handler.getLastLoggedInEmail();
            if (lastEmail != null) {
                Reader last = handler.findReaderByEmail(lastEmail);
                if (last != null) {
                    current = last;
                    current.incrementLoginCount();
                    System.out.println(Localizator.tr("login.auto", current.getName(), current.getSurname()));
                    System.out.println(Localizator.tr("login.count", current.getSessionLoginCount()));
                }
            }

            while (current == null) {
                System.out.println("\n" + Localizator.tr("menu.user.none"));
                System.out.println(Localizator.tr("login.options.1"));
                System.out.println(Localizator.tr("login.options.2"));
                System.out.println(Localizator.tr("login.options.3"));
                System.out.print(Localizator.tr("menu.choice") + " ");
                String startChoice = scanner.nextLine().trim();
                switch (startChoice) {
                    case "1":
                        System.out.print(Localizator.tr("login.prompt.email") + " ");
                        String email = scanner.nextLine().trim();
                        Reader found = handler.findReaderByEmail(email);
                        if (found != null) {
                            current = found;
                            current.incrementLoginCount();
                            handler.setLastLoggedInEmail(email);
                            System.out.println(Localizator.tr("login.logged", current.getName(), current.getSurname()));
                        } else {
                            System.out.println(Localizator.tr("login.notfound"));
                        }
                        break;
                    case "2":
                        current = createAndRegisterReader(handler, scanner);
                        break;
                    case "3":
                        current = createTempReader(scanner);
                        System.out.println(Localizator.tr("temp.proceed", current.getName(), current.getSurname()));
                        break;
                    default:
                        System.out.println(Localizator.tr("menu.unknown"));
                }
            }

            boolean running = true;
            while (running) {
                System.out.println("\n" + Localizator.tr("menu"));
                System.out.println(Localizator.tr("menu.1"));
                System.out.println(Localizator.tr("menu.2"));
                System.out.println(Localizator.tr("menu.3"));
                System.out.println(Localizator.tr("menu.4"));
                System.out.println(Localizator.tr("menu.5"));
                System.out.println(Localizator.tr("menu.6"));
                System.out.println(Localizator.tr("menu.7"));
                System.out.println(Localizator.tr("menu.8"));
                System.out.print(Localizator.tr("menu.choice") + " ");
                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1":
                            handler.printCatalogue();
                            break;
                        case "2":
                            System.out.print(Localizator.tr("borrow.prompt.title") + " ");
                            String title = scanner.nextLine().trim();
                            System.out.print(Localizator.tr("borrow.prompt.rr") + " ");
                            String rr = scanner.nextLine().trim();
                            boolean readingRoom = Localizator.isYes(rr);
                            try {
                                Record r = handler.placeOrderByTitle(current, title, readingRoom);
                                System.out.println(Localizator.tr("borrow.ok", r.getBook(), Localizator.fmt(r.getDueDate())));
                            } catch (RuntimeException ex) {
                                System.out.println(Localizator.tr("borrow.fail", ex.getMessage()));
                            }
                            break;
                        case "3":
                            List<Record> mine = handler.getRecordsByReader(current);
                            if (mine.isEmpty()) {
                                System.out.println(Localizator.tr("loans.none"));
                            } else {
                                System.out.println(Localizator.tr("loans.title"));
                                for (int i = 0; i < mine.size(); i++) {
                                    System.out.printf("[%d] %s%n", i + 1, mine.get(i));
                                }
                            }
                            break;
                        case "4":
                            List<Record> mine2 = handler.getRecordsByReader(current);
                            if (mine2.isEmpty()) {
                                System.out.println(Localizator.tr("loans.return.none"));
                                break;
                            }
                            System.out.println(Localizator.tr("loans.return.select"));
                            for (int i = 0; i < mine2.size(); i++) {
                                System.out.printf("[%d] %s%n", i + 1, mine2.get(i));
                            }
                            System.out.print(Localizator.tr("loans.return.enter") + " ");
                            String idxStr = scanner.nextLine().trim();
                            int idx = Integer.parseInt(idxStr) - 1;
                            if (idx < 0 || idx >= mine2.size()) {
                                System.out.println(Localizator.tr("loans.return.invalid"));
                            } else {
                                handler.returnBook(mine2.get(idx));
                            }
                            break;
                        case "5":
                            System.out.print(Localizator.tr("overdue.simulate") + " ");
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
                            System.out.println(Localizator.tr("switch.title"));
                            System.out.println(Localizator.tr("switch.title2"));
                            System.out.print(Localizator.tr("switch.choose") + " ");
                            String sub = scanner.nextLine().trim();
                            if ("1".equals(sub)) {
                                current = createAndRegisterReader(handler, scanner);
                            } else if ("2".equals(sub)) {
                                System.out.print(Localizator.tr("reader.prompt.email") + " ");
                                String email2 = scanner.nextLine().trim();
                                Reader found2 = handler.findReaderByEmail(email2);
                                if (found2 != null) {
                                    current = found2;
                                    current.incrementLoginCount();
                                    handler.setLastLoggedInEmail(email2);
                                    System.out.println(Localizator.tr("switch.switched", current.getName(), current.getSurname()));
                                } else {
                                    System.out.println(Localizator.tr("login.notfound"));
                                }
                            } else {
                                System.out.println(Localizator.tr("menu.unknown"));
                            }
                            break;
                        default:
                            System.out.println(Localizator.tr("menu.unknown"));
                    }
                } catch (Exception ex) {
                    System.err.println(Localizator.tr("error.generic", ex.getMessage()));
                }
            }
        }

        InventoryFileStorage.saveCatalogueToFile(handler.getCatalogue());
        connector.saveState(handler);
    }

    private static Reader createAndRegisterReader(LibraryHandler handler, Scanner scanner) {
        System.out.println(Localizator.tr("register.enter"));
        Reader r = gatherInformation(scanner);
        handler.addReader(r);
        handler.setLastLoggedInEmail(r.getEmail());
        System.out.println(Localizator.tr("register.ok", r.getName(), r.getSurname()));
        return r;
    }

    private static Reader createTempReader(Scanner scanner) {
        System.out.println(Localizator.tr("register.enter"));
        return gatherInformation(scanner);
    }
}