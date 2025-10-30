import java.io.Serial;
import java.io.Serializable;

public class Reader implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String surname;
    private final String patronymic;
    private final String address;
    private final String email;
    private final String phone_number;

    public Reader(String name, String surname, String patronymic, String address, String email, String phone_number) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
    }

    // getters
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPatronymic() { return patronymic; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phone_number; }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Surname: " + surname + "\n" +
                "Patronymic: " + patronymic + "\n" +
                "Address: " + address + "\n" +
                "Email: " + email + "\n" +
                "Phone Number: " + phone_number + "\n";
    }

    // equality to identify readers (used for blacklist etc.)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader other = (Reader) o;
        // use email as primary unique field if present, otherwise compare full name
        if (email != null && other.email != null) return email.equals(other.email);
        return name.equals(other.name) && surname.equals(other.surname) && patronymic.equals(other.patronymic);
    }

    @Override
    public int hashCode() {
        if (email != null) return email.hashCode();
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + patronymic.hashCode();
        return result;
    }
}