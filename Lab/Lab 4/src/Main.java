public class Main {
    public static void main(String[] args) {
        Group g = new Group(FileHandler.ReadFile("input.txt"));
        FileHandler.WriteFile(g.getListOfGrade(4), "grade_4.txt");
        FileHandler.WriteFile(g.getListOfExcellent(), "excellent.txt");
        System.out.println("Число отличников: " + g.getListOfExcellent().size());
        System.out.println("Средний балл группы " + g.getGroupName() + ": " + g.getAverageGrade());
    }

}