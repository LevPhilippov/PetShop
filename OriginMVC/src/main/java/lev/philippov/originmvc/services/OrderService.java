package lev.philippov.originmvc.services;

import lev.philippov.originmvc.domain.OrderItem;
import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.domain.Order;
import lev.philippov.originmvc.domain.OrderDetails;
import lev.philippov.originmvc.domain.OrderStatus;
import lev.philippov.originmvc.repositories.OrderRepository;
import lev.philippov.originmvc.services.mappers.OrderItemMapper;
import lev.philippov.originmvc.services.rabbitmq.OrderConfirmationService;
import lev.philippov.originmvc.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderConfirmationService orderConfirmationService;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public Order saveOrder(Cart cart, Principal principal, OrderDetails details) {
        String userId = principal.getName();
        List<OrderItem> orderItems = cart.getOrderItems().stream().map(orderItemMapper::orderItemDtoToOrderItem).collect(Collectors.toList());
        Order order = new Order(orderItems,cart.getTotalPrice(), userId, details);
        orderRepository.save(order);
        orderConfirmationService.requestMQConfirmation(order.getId());
        return order;
    }

    @Transactional
    public Order saveAnonymousOrder(Cart cart,OrderDetails details) {
        List<OrderItem> orderItems = cart.getOrderItems().stream().map(orderItemMapper::orderItemDtoToOrderItem).collect(Collectors.toList());
        Order order = new Order(orderItems, cart.getTotalPrice(),details);
        orderRepository.save(order);
        orderConfirmationService.requestMQConfirmation(order.getId());
        return order;
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
