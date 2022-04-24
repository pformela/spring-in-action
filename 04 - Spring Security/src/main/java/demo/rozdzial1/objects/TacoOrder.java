package demo.rozdzial1.objects;

import demo.rozdzial1.user.User;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="Taco_Order")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Date placed_at;

    @ManyToOne
    private User user;

    @NotBlank(message="Delivery name is required")
    private String delivery_name;

    @NotBlank(message="Street is required")
    private String delivery_street;

    @NotBlank(message="City is required")
    private String delivery_city;

    @NotBlank(message="State is required")
    private String delivery_state;

    @NotBlank(message="Zip code is required")
    private String delivery_zip;

    @CreditCardNumber(message="Not a valid credit card number")
    private String cc_number;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private String cc_expiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @ManyToMany(targetEntity=Taco.class)
    @ToString.Exclude
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist
    void placedAt() {
        this.placed_at = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TacoOrder tacoOrder = (TacoOrder) o;
        return id != null && Objects.equals(id, tacoOrder.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
