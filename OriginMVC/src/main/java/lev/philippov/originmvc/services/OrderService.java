package lev.philippov.originmvc.services;

import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.domain.Order;
import lev.philippov.originmvc.domain.OrderDetails;
import lev.philippov.originmvc.domain.OrderStatus;
import lev.philippov.originmvc.repositories.OrderRepository;
import lev.philippov.originmvc.services.rabbitmq.OrderConfirmationService;
import lev.philippov.originmvc.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Set;

@Service
public class OrderService {

    OrderRepository orderRepository;
    OrderConfirmationService orderConfirmationService;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderConfirmationService(OrderConfirmationService orderConfirmationService) {
        this.orderConfirmationService = orderConfirmationService;
    }

    @Transactional
    public Order saveOrder(Cart cart, Principal principal, OrderDetails details) {
        String userId = principal.getName();
        Order order = new Order(cart, userId, details);
        orderRepository.save(order);
        orderConfirmationService.requestMQConfirmation(order.getId());
        return order;
    }

    @Transactional
    public Order saveAnonymousOrder(Cart cart,OrderDetails details) {
        Order order = new Order(cart,details);
        orderRepository.save(order);
        orderConfirmationService.requestMQConfirmation(order.getId());
        return order;
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


    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new ServerException(String.format("Order with Id %d wasn't found!",orderId)));
    }

    public Set<Order> findAllByOrderDetailsPhone(String phone) {
        return orderRepository.findAllByOrderDetailsPhone(phone);
    }

    public void saveAll(Set<Order> orders) {
        orderRepository.saveAll(orders);
    }
}
