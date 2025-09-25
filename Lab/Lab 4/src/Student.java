public class Student {
    private String surname;
    private String name;
    private String patronymic;
    private int course;
    private String group;
    private GradeBook gradeBook;

    public Student(String surname, String name, String patronymic, int course, String group, int session) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.course = course;
        this.group = group;
        this.gradeBook = new GradeBook(session);
    }

    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getPatronymic() { return patronymic; }
    public int getCourse() { return course; }
    public String getGroup() { return group; }
    public GradeBook getGradeBook() { return gradeBook; }

    public double getAverageGrade(){
        double sum = 0;
        for(var grade : getGradeBook().getGrades()){
            sum += grade.getScore();
        }
        return sum / getGradeBook().getGrades().size();
    }
    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic +
                ", Курс: " + course + ", Группа: " + group + "\n" + gradeBook.toString();
    }
}