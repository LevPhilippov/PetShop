package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findAllByUserId(String userId);

    Set<Order> findAllByOrderDetailsPhone(String phone);
}
