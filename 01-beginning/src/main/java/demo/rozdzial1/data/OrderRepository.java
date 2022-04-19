package demo.rozdzial1.data;

import demo.rozdzial1.objects.Order;

public interface OrderRepository {
    Order save(Order order);
}
