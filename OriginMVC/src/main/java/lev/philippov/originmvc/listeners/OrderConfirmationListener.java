package lev.philippov.originmvc.listeners;

import lev.philippov.originmvc.domain.Order;
import lev.philippov.originmvc.events.OrderConfirmationEvent;
import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class OrderConfirmationListener {

    private final OrderRepository orderRepository;

    @EventListener
    @Transactional
    public void listen(OrderConfirmationEvent event){
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow(() -> new ServerException("Unknown order Id due to updating order status."));
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.saveAndFlush(order);
    }
}
