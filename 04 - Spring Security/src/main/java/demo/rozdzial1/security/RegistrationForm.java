package demo.rozdzial1.security;

import demo.rozdzial1.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String full_name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(BCryptPasswordEncoder encoder) {
        return new User(username, password, full_name, street, city, state, zip, phone);
    }
}
