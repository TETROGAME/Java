public class Reader {
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String address;
    private final String email;
    private final String phone_number;
    Reader(String name, String surname, String patronymic, String address, String email, String phone_number) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
    }


    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Surname: " + surname + "\n" +
                "Patronymic: " + patronymic + "\n" +
                "Address: " + address + "\n" +
                "Email: " + email + "\n" +
                "Phone Number: " + phone_number + "\n";
    }
}
