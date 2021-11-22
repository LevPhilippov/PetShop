package lev.philippov.originmvc.services;

import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.models.OrderDetails;
import lev.philippov.originmvc.models.User;
import lev.philippov.originmvc.repositories.OrderRepository;
import lev.philippov.originmvc.utils.Cart;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.Principal;

@Service
public class CartService {

    OrderRepository orderRepository;
    UserService userService;

    @PersistenceContext
    EntityManager entityManager;

    public CartService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveOrder(Cart cart, Principal principal, OrderDetails details) {
        User user = userService.findByUsername(principal.getName());
        Order order = new Order(cart, user, details);
        orderRepository.save(order);
    }

    public void saveAnonymousOrder(Cart cart,OrderDetails details) {
        Order order = new Order(cart,null,details);
        orderRepository.save(order);
    }
}
