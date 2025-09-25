import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static ArrayList<Student> ReadFile(String path) {
        ArrayList<Student> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(path))) {
            Pattern studentPattern = Pattern.compile(
                    "^([А-Яа-яA-Za-z]+)\\s+([А-Яа-яA-Za-z]+)\\s+([А-Яа-яA-Za-z]+),\\s*Курс:\\s*(\\d+),\\s*Группа:\\s*(.+)$");
            Pattern sessionPattern = Pattern.compile("^Сессия:\\s*(\\d+)$");
            Pattern gradePattern = Pattern.compile("^([А-Яа-яA-Za-z]+):\\s*(\\d+)$");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                Matcher studentMatcher = studentPattern.matcher(line);
                if (studentMatcher.matches()) {
                    String surname = studentMatcher.group(1);
                    String name = studentMatcher.group(2);
                    String patronymic = studentMatcher.group(3);
                    int course = Integer.parseInt(studentMatcher.group(4));
                    String group = studentMatcher.group(5);

                    int session = 0;
                    ArrayList<GradeBook.Grade> grades = new ArrayList<>();

                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                        if (line.isEmpty()) continue;
                        Matcher sessionMatcher = sessionPattern.matcher(line);
                        if (sessionMatcher.matches()) {
                            session = Integer.parseInt(sessionMatcher.group(1));
                            break;
                        }
                    }

                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                        if (line.equals("Grades:")) break;
                    }
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine().trim();
                        if (line.isEmpty()) break;
                        Matcher gradeMatcher = gradePattern.matcher(line);
                        if (gradeMatcher.matches()) {
                            String subject = gradeMatcher.group(1);
                            int score = Integer.parseInt(gradeMatcher.group(2));
                            grades.add(new GradeBook(session).new Grade(subject, score));
                        } else {
                            break;
                        }
                    }

                    Student student = new Student(surname, name, patronymic, course, group, session);
                    for (GradeBook.Grade grade : grades) {
                        student.getGradeBook().addGrade(grade.getSubject(), grade.getScore());
                    }
                    list.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    public static void WriteFile(ArrayList<Student> list, String path) {
        if(list.isEmpty()){
            System.out.println("Provided ArrayList is empty");
            System.exit(1);
        }
        try(FileWriter fileWriter = new FileWriter(path)){
            for(var stud : list){
                fileWriter.write(stud.toString());
            }
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static Group initGroup(String groupName) {
        Group group = new Group(groupName);

        Student s1 = new Student("Иванов", "Иван", "Иванович", 1, groupName, 1);
        s1.getGradeBook().addGrade("Математика", 10);
        s1.getGradeBook().addGrade("Информатика", 9);
        s1.getGradeBook().addGrade("Физика", 8);
        group.addStudent(s1);

        Student s2 = new Student("Петров", "Петр", "Петрович", 2, groupName, 2);
        s2.getGradeBook().addGrade("Математика", 7);
        s2.getGradeBook().addGrade("Информатика", 8);
        s2.getGradeBook().addGrade("Физика", 9);
        group.addStudent(s2);

        Student s3 = new Student("Сидоров", "Сидор", "Сидорович", 1, groupName, 1);
        s3.getGradeBook().addGrade("Математика", 10);
        s3.getGradeBook().addGrade("Информатика", 10);
        s3.getGradeBook().addGrade("Физика", 10);
        group.addStudent(s3);

        Student s4 = new Student("Алексеева", "Анна", "Алексеевна", 3, groupName, 3);
        s4.getGradeBook().addGrade("Математика", 8);
        s4.getGradeBook().addGrade("Информатика", 8);
        s4.getGradeBook().addGrade("Физика", 8);
        group.addStudent(s4);

        Student s5 = new Student("Кузнецов", "Кирилл", "Кузьмич", 2, groupName, 2);
        s5.getGradeBook().addGrade("Математика", 9);
        s5.getGradeBook().addGrade("Информатика", 8);
        s5.getGradeBook().addGrade("Физика", 9);
        group.addStudent(s5);

        Student s6 = new Student("Морозова", "Мария", "Морозовна", 1, groupName, 1);
        s6.getGradeBook().addGrade("Математика", 6);
        s6.getGradeBook().addGrade("Информатика", 7);
        s6.getGradeBook().addGrade("Физика", 8);
        group.addStudent(s6);

        Student s7 = new Student("Волков", "Владимир", "Волкович", 4, groupName, 4);
        s7.getGradeBook().addGrade("Математика", 10);
        s7.getGradeBook().addGrade("Информатика", 10);
        s7.getGradeBook().addGrade("Физика", 9);
        group.addStudent(s7);

        Student s8 = new Student("Зайцева", "Зоя", "Зайцевна", 3, groupName, 3);
        s8.getGradeBook().addGrade("Математика", 7);
        s8.getGradeBook().addGrade("Информатика", 8);
        s8.getGradeBook().addGrade("Физика", 9);
        group.addStudent(s8);

        Student s9 = new Student("Орлов", "Олег", "Орлович", 2, groupName, 2);
        s9.getGradeBook().addGrade("Математика", 9);
        s9.getGradeBook().addGrade("Информатика", 9);
        s9.getGradeBook().addGrade("Физика", 9);
        group.addStudent(s9);

        Student s10 = new Student("Смирнова", "Светлана", "Смирновна", 4, groupName, 4);
        s10.getGradeBook().addGrade("Математика", 10);
        s10.getGradeBook().addGrade("Информатика", 10);
        s10.getGradeBook().addGrade("Физика", 10);
        group.addStudent(s10);

        return group;
    }

    public static void main(String[] args) {
        WriteFile(initGroup("Группа 5").getStudents(), "input.txt");
        System.out.print(ReadFile("input.txt"));

    }

}