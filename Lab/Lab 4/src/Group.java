import java.util.ArrayList;

public class Group {
    private String groupName;
    private ArrayList<Student> students;

    public Group(String groupName) {
        this.groupName = groupName;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (!student.getGroup().equals(groupName)) {
            System.out.println("Студент не принадлежит группе " + groupName);
            return;
        }
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group: ").append(groupName).append("\n");
        for (Student s : students) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}