package demo.rozdzial1.objects;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class Taco {

    private Long id;
    private Date createdAt;

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    @NotEmpty(message="Musisz wybrać przynajmniej jeden składnik")
    @ToString.Exclude
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}