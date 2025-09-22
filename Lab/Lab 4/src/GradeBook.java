import java.util.*;

public class GradeBook {
    private Person person;
    private List<Session> sessions;

    public GradeBook(Person person) {
        this.person = person;
        this.sessions = new ArrayList<>();
    }

    public class Session {
        private int sessionNumber;
        private ArrayList<Exam> exams = new ArrayList<>();
        private ArrayList<Credit> credits = new ArrayList<>();

        public Session(int sessionNumber) {
            this.sessionNumber = sessionNumber;
        }

        public void addExam(String subject, int grade) { exams.add(new Exam(subject, grade)); }
        public void addCredit(String subject, boolean passed) { credits.add(new Credit(subject, passed)); }

        public int getSessionNumber() { return sessionNumber; }
        public ArrayList<Exam> getExams() { return exams; }
        public ArrayList<Credit> getCredits() { return credits; }

        public class Exam {
            private String subject;
            private int grade;

            public Exam(String subject, int grade) {
                this.subject = subject;
                this.grade = grade;
            }

            public String getSubject() { return subject; }
            public int getGrade() { return grade; }
        }

        public class Credit {
            private String subject;
            private boolean passed;

            public Credit(String subject, boolean passed) {
                this.subject = subject;
                this.passed = passed;
            }

            public String getSubject() { return subject; }
            public boolean isPassed() { return passed; }
        }
    }

    public void addSession(Session session) { sessions.add(session); }

    public List<Session> getSessions() { return sessions; }

    public boolean isExcellent() {
        for (Session session : sessions) {
            for (Session.Exam exam : session.getExams()) {
                if (exam.getGrade() < 9) return false;
            }
            for (Session.Credit credit : session.getCredits()) {
                if (!credit.isPassed()) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return this.person.toString() +
                "\n";
    }
}