package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
