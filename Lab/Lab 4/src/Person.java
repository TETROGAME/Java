public class Person{
    private String name;
    private String surname;
    private String patronymic;
    private int course;
    private int group;

    public String getName(){ return this.name; }
    public String getSurname(){ return this.surname; }
    public String getPatronymic(){ return this.patronymic; }
    public int getCourse_of_study(){ return this.course; }
    public int getGroup(){ return this.group; }


    public void setName(String name){ this.name = name; }
    public void setSurname(String surname){ this.surname = surname; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public void setCourse_of_study(int course){ this.course = course; }
    public void setGroup(int group){ this.group = group; }
    Person(){
        this.name = "";
        this.surname = "";
        this.patronymic = "";
        this.course = 0;
        this.group = 0;
    }
    Person(String name, String surname, String patronymic, int age, int course, int group){
        this.name=name;
        this.surname=surname;
        this.patronymic=patronymic;
        this.course = course;
        this.group=group;
    }

    @Override
    public String toString(){
        return "Name: ".concat(this.name) +
                "\nSurname: ".concat(this.surname) +
                "\nPatronymic: ".concat(this.patronymic) +
                "\nCourse of study".concat(Integer.toString(this.course)) +
                "\nGroup:".concat(Integer.toString(this.group)) ;
    }
}