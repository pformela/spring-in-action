package demo.rozdzial1.data;

import demo.rozdzial1.objects.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, String> {
}
