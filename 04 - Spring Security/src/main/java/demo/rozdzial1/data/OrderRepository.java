package demo.rozdzial1.data;

import demo.rozdzial1.objects.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
