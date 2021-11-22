package lev.philippov.originmvc.services;

import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.models.OrderDetails;
import lev.philippov.originmvc.models.OrderStatus;
import lev.philippov.originmvc.models.User;
import lev.philippov.originmvc.repositories.OrderRepository;
import lev.philippov.originmvc.services.rabbitmq.OrderConfirmationService;
import lev.philippov.originmvc.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.function.Supplier;

@Service
public class CartService {

    OrderRepository orderRepository;
    UserService userService;
    OrderConfirmationService orderConfirmationService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setOrderConfirmationService(OrderConfirmationService orderConfirmationService) {
        this.orderConfirmationService = orderConfirmationService;
    }

    @Transactional
    public void saveOrder(Cart cart, Principal principal, OrderDetails details) {
        User user = userService.findByUsername(principal.getName());
        Order order = new Order(cart, user, details);
        orderRepository.save(order);
        orderConfirmationService.requestMQConfirmation(order.getId());
    }

    @Transactional
    public void saveAnonymousOrder(Cart cart,OrderDetails details) {
        Order order = new Order(cart,null,details);
        orderRepository.save(order);
        orderConfirmationService.requestMQConfirmation(order.getId());
    }

    @Transactional
    public void changeOrderStatus(String token) {
        if(token.matches("^[0-9]*_OK$")){
            Long orderId  = Long.parseLong(token.split("_")[0]);
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ServerException("Unknown order Id due to updating order status."));
            order.setOrderStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } else if (token.matches("^[0-9]*_NOT$")){
            Long orderId  = Long.parseLong(token.split("_")[0]);
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ServerException("Unknown order Id due to updating order status."));
            order.setOrderStatus(OrderStatus.NOT_CONFIRMED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Unknown token from RabbitMQ: " + token);
        }
    }


}
