package demo.rozdzial1.data;

import demo.rozdzial1.objects.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
