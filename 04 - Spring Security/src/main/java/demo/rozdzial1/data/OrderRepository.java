package demo.rozdzial1.data;

import demo.rozdzial1.objects.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
}
