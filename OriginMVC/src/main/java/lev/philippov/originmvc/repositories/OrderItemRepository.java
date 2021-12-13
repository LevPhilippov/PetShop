package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
