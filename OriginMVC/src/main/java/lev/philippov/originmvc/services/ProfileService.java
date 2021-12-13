package lev.philippov.originmvc.services;

import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.repositories.OrderRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfileService {

    OrderRepository orderRepository;

    public ProfileService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Set<Order> findAllOrdersByUser(Long userId) throws UsernameNotFoundException {

//        User user = userService.findById(userId);
//        return orderRepository.findAllByUser(user);
        return null;
    }
}
