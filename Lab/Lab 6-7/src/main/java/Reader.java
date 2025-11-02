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
    private transient int sessionLoginCount = 0;

    public Reader(String name, String surname, String patronymic, String address, String email, String phone_number) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPatronymic() { return patronymic; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phone_number; }

    public void incrementLoginCount() {
        this.sessionLoginCount++;
    }

    public int getSessionLoginCount() {
        return this.sessionLoginCount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader other)) return false;
        if (email != null && !email.isBlank() && other.email != null && !other.email.isBlank()) {
            return email.equalsIgnoreCase(other.email);
        }
        return name.equals(other.name) && surname.equals(other.surname) && patronymic.equals(other.patronymic);
    }

    @Override
    public int hashCode() {
        if (email != null && !email.isBlank()) {
            return email.toLowerCase().hashCode();
        }
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + patronymic.hashCode();
        return result;
    }
}