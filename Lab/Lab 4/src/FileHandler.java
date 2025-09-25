import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
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
}
