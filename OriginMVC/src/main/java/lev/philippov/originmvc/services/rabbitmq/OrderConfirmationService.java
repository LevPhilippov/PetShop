package lev.philippov.originmvc.services.rabbitmq;

import lev.philippov.originmvc.config.RabbitMQConfig;
import lev.philippov.originmvc.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderConfirmationService {

    private OrderService orderService;
    private RabbitTemplate rabbitTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    public void setCartService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void requestMQConfirmation(Long orderId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGER_NAME,RabbitMQConfig.ROUTING_KEY, orderId.toString());
    }

    public void receive(byte[] bytes){
        String token = new String(bytes);
        logger.info("Received message: {}",token);
        orderService.changeOrderStatus(token);
    }

}
