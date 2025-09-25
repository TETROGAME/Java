public class Main {
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
        FileHandler.WriteFile(initGroup("Группа 5").getStudents(), "input.txt");
        System.out.print(FileHandler.ReadFile("input.txt"));

    }

}