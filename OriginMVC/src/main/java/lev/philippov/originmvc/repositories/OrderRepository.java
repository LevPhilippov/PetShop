package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findAllByUserId(String userId);

    Set<Order> findAllByOrderDetailsPhone(String phone);
}
