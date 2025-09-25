import java.util.*;

public class GradeBook {
    private int session;
    private ArrayList<Grade> grades;

    public GradeBook(int session) {
        this.session = session;
        this.grades = new ArrayList<>();
    }

    public int getSession() {
        return session;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public class Grade {
        private String subject;
        private int score;

        public Grade(String subject, int score) {
            this.subject = subject;
            this.score = score;
        }

        public String getSubject() { return subject; }
        public int getScore() { return score; }
        @Override
        public String toString() {
            return subject + ": " + score;
        }
    }

    public void addGrade(String subject, int score) {
        grades.add(new Grade(subject, score));
    }

    public Integer getGrade(String subject) {
        for(Grade g : grades) {
            if(g.subject.equals(subject)) {
                return g.getScore();
            }
        }
        return -1;
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (Grade grade : grades) {
            sum += grade.getScore();
        }
        return sum / (double) grades.size();
    }

    public boolean isExcellent() {
        if (grades.isEmpty()) return false;
        for (Grade grade : grades) {
            if (grade.getScore() < 9) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Сессия: ").append(session).append("\n");
        sb.append("Grades:\n");
        for (Grade grade : grades) {
            sb.append(grade.toString()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}