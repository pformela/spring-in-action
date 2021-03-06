package demo.rozdzial1.objects;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class Order {
    @NotBlank(message = "Podanie imienia i nazwiska jest obowiązkowe.")
    @Size(max=50, message="Name must be max 50 characters long")
    private String name;

    @NotBlank(message = "Podanie ulicy jest obowiązkowe.")
    @Size(max=50, message="Name must be max 50 characters long")
    private String street;

    @NotBlank(message = "Podanie miejscowości jest obowiązkowe.")
    @Size(max=50, message="Name must be max 50 characters long")
    private String city;

    @NotBlank(message = "Podanie województwa jest obowiązkowe.")
    @Size(max=30, message="Name must be max 30 characters long")
    private String state;

    @NotBlank(message = "Podanie kodu pocztowego jest obowiązkowe.")
    @Size(min=6, max=6, message="Name must 6 characters long")
    private String zip;

    @CreditCardNumber(message="To nie jest prawidłowy numer karty kredytowej.")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Wartość musi być w formacie MM/RR.")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Nieprawidłowy kod CVV.")
    private String ccCVV;

    private Long id;
    private Date placedAt;

    private List<Taco> tacos = new ArrayList<>();
    public void addDesign(Taco taco) {
        tacos.add(taco);
    }
}
