import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "bills.dat";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (RepairBillFileManager manager = new RepairBillFileManager(FILE_NAME)) {
            boolean exit = false;
            while (!exit) {
                printMenu();
                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1":
                            fillWithTestData(manager);
                            break;
                        case "2":
                            printAllSequential(manager);
                            break;
                        case "3":
                            buildAndUseIndex(manager);
                            break;
                        case "0":
                            exit = true;
                            break;
                        default:
                            System.out.println("Неизвестный пункт меню");
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("=== Меню ===");
        System.out.println("1. Заполнить файл тестовыми данными");
        System.out.println("2. Последовательный вывод всех объектов без сортировки");
        System.out.println("3. Индексно-последовательный доступ (индексирование и операции)");
        System.out.println("0. Выход");
        System.out.print("Выберите пункт: ");
    }

    private static void fillWithTestData(RepairBillFileManager manager) throws IOException {
        System.out.print("Очистить файл перед заполнением? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
        if (ans.equals("y") || ans.equals("yes")) {
            manager.clearFile();
        }

        List<RepairBill> sample = Arrays.asList(
                new RepairBill("AlphaService", "Plumbing", "hour", 50.0, LocalDate.of(2025, 1, 10), 3),
                new RepairBill("AlphaService", "Electric", "hour", 60.0, LocalDate.of(2025, 1, 12), 2),
                new RepairBill("BetaRepair", "Painting", "m2", 15.0, LocalDate.of(2025, 2, 1), 20),
                new RepairBill("GammaFix", "Plumbing", "hour", 55.0, LocalDate.of(2025, 1, 15), 5),
                new RepairBill("BetaRepair", "Electric", "hour", 65.0, LocalDate.of(2025, 3, 5), 1)
        );

        for (RepairBill bill : sample) {
            long offset = manager.appendRecord(bill);
            System.out.println("Добавлена запись по смещению " + offset + ": " + bill);
        }
    }

    private static void printAllSequential(RepairBillFileManager manager) throws IOException {
        System.out.println("=== Последовательный вывод ===");
        List<RepairBillFileManager.RecordMeta> metas = manager.readAllRecordsMeta();
        for (RepairBillFileManager.RecordMeta meta : metas) {
            if (meta.deleted) {
                continue;
            }
            try {
                RepairBill bill = manager.readRecordAt(meta.offset);
                if (bill != null) {
                    System.out.println("offset=" + meta.offset + " -> " + bill);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Невозможно прочитать запись по смещению " + meta.offset);
            }
        }
    }

    private static void buildAndUseIndex(RepairBillFileManager manager) throws IOException {
        String field = chooseIndexField();
        if (field == null) return;

        Map<String, List<Long>> index = manager.buildIndex(field);
        if (index.isEmpty()) {
            System.out.println("Файл пуст или нет активных записей.");
            return;
        }
        System.out.println("Индекс построен по полю: " + field);
        boolean back = false;
        while (!back) {
            printIndexMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    printAllByIndex(manager, index, field);
                    break;
                case "2":
                    searchEquals(manager, index, field);
                    break;
                case "3":
                    searchGreater(manager, index, field);
                    break;
                case "4":
                    searchLess(manager, index, field);
                    break;
                case "5":
                    deleteByIndex(manager, index, field);
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Неизвестная команда");
            }
        }
    }

    private static String chooseIndexField() {
        System.out.println("Индексировать по:");
        System.out.println("1. фирма");
        System.out.println("2. вид работ");
        System.out.println("3. дата исполнения");
        System.out.print("Выберите: ");
        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                return "firm";
            case "2":
                return "workType";
            case "3":
                return "date";
            default:
                System.out.println("Некорректный выбор");
                return null;
        }
    }

    private static void printIndexMenu() {
        System.out.println("=== Операции с индексом ===");
        System.out.println("1. Вывод всех объектов по возрастанию/убыванию индекса");
        System.out.println("2. Поиск и вывод объекта по индексу (=)");
        System.out.println("3. Поиск и вывод объекта по индексу (>)");
        System.out.println("4. Поиск и вывод объекта по индексу (<)");
        System.out.println("5. Удаление объекта по индексу");
        System.out.println("0. Назад в главное меню");
        System.out.print("Выберите: ");
    }

    private static void printAllByIndex(RepairBillFileManager manager,
                                        Map<String, List<Long>> index,
                                        String field) {
        System.out.print("Вывести по возрастанию (asc) или убыванию (desc)? ");
        String order = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

        List<String> keys = new ArrayList<>(index.keySet());
        Comparator<String> cmp;
        if (field.equals("date")) {
            cmp = Comparator.comparing(RepairBill::parseDate);
        } else {
            cmp = String::compareTo;
        }
        if (order.equals("desc")) {
            cmp = cmp.reversed();
        }
        keys.sort(cmp);

        for (String key : keys) {
            System.out.println("=== key = " + key + " ===");
            List<Long> offsets = index.get(key);
            for (Long off : offsets) {
                try {
                    RepairBill bill = manager.readRecordAt(off);
                    if (bill != null) {
                        System.out.println("offset=" + off + " -> " + bill);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка чтения по смещению " + off + ": " + e.getMessage());
                }
            }
        }
    }

    private static void searchEquals(RepairBillFileManager manager,
                                     Map<String, List<Long>> index,
                                     String field) {
        String key = readIndexKey(field);
        if (key == null) return;

        List<Long> offsets = index.get(key);
        if (offsets == null || offsets.isEmpty()) {
            System.out.println("По данному значению индекса записи не найдены.");
            return;
        }
        for (Long off : offsets) {
            try {
                RepairBill bill = manager.readRecordAt(off);
                if (bill != null) {
                    System.out.println("offset=" + off + " -> " + bill);
                }
            } catch (Exception e) {
                System.out.println("Ошибка чтения по смещению " + off + ": " + e.getMessage());
            }
        }
    }

    private static void searchGreater(RepairBillFileManager manager,
                                      Map<String, List<Long>> index,
                                      String field) {
        String key = readIndexKey(field);
        if (key == null) return;

        List<String> keys = new ArrayList<>(index.keySet());
        Comparator<String> cmp = field.equals("date")
                ? Comparator.comparing(RepairBill::parseDate)
                : String::compareTo;
        keys.sort(cmp);

        for (String k : keys) {
            if (cmp.compare(k, key) > 0) {
                List<Long> offsets = index.get(k);
                for (Long off : offsets) {
                    try {
                        RepairBill bill = manager.readRecordAt(off);
                        if (bill != null) {
                            System.out.println("key=" + k + " offset=" + off + " -> " + bill);
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка чтения по смещению " + off + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    private static void searchLess(RepairBillFileManager manager,
                                   Map<String, List<Long>> index,
                                   String field) {
        String key = readIndexKey(field);
        if (key == null) return;

        List<String> keys = new ArrayList<>(index.keySet());
        Comparator<String> cmp = field.equals("date")
                ? Comparator.comparing(RepairBill::parseDate)
                : String::compareTo;
        keys.sort(cmp);

        for (String k : keys) {
            if (cmp.compare(k, key) < 0) {
                List<Long> offsets = index.get(k);
                for (Long off : offsets) {
                    try {
                        RepairBill bill = manager.readRecordAt(off);
                        if (bill != null) {
                            System.out.println("key=" + k + " offset=" + off + " -> " + bill);
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка чтения по смещению " + off + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    private static void deleteByIndex(RepairBillFileManager manager,
                                      Map<String, List<Long>> index,
                                      String field) throws IOException {
        String key = readIndexKey(field);
        if (key == null) return;

        List<Long> offsets = index.get(key);
        if (offsets == null || offsets.isEmpty()) {
            System.out.println("По данному индексу записи не найдены.");
            return;
        }
        System.out.println("Найдено записей: " + offsets.size());
        for (int i = 0; i < offsets.size(); i++) {
            long off = offsets.get(i);
            try {
                RepairBill bill = manager.readRecordAt(off);
                if (bill != null) {
                    System.out.println((i + 1) + ") offset=" + off + " -> " + bill);
                } else {
                    System.out.println((i + 1) + ") offset=" + off + " -> (уже удалена)");
                }
            } catch (Exception e) {
                System.out.println((i + 1) + ") offset=" + off + " -> ошибка чтения: " + e.getMessage());
            }
        }
        System.out.print("Введите номер записи для удаления (0 — отмена): ");
        String s = scanner.nextLine().trim();
        int idx;
        try {
            idx = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный номер");
            return;
        }
        if (idx <= 0 || idx > offsets.size()) {
            System.out.println("Отмена или некорректный номер");
            return;
        }
        long offsetToDelete = offsets.get(idx - 1);
        manager.markDeleted(offsetToDelete);
        System.out.println("Запись по смещению " + offsetToDelete + " помечена как удаленная.");

        offsets.remove(idx - 1);
        if (offsets.isEmpty()) {
            index.remove(key);
        }
    }

    private static String readIndexKey(String field) {
        System.out.print("Введите значение индекса (" + fieldDescription(field) + "): ");
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            System.out.println("Пустое значение недопустимо.");
            return null;
        }
        if (field.equals("date")) {
            try {
                java.time.format.DateTimeFormatter fmt =
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate.parse(value, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Используйте yyyy-MM-dd.");
                return null;
            }
        }
        return value;
    }

    private static String fieldDescription(String field) {
        switch (field) {
            case "firm":
                return "фирма (строка)";
            case "workType":
                return "вид работ (строка)";
            case "date":
                return "дата исполнения (yyyy-MM-dd)";
            default:
                return field;
        }
    }
}