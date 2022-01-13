package lev.philippov.originmvc.services.rabbitmq;

import lev.philippov.originmvc.config.RabbitMQConfig;
import lev.philippov.originmvc.domain.OrderStatus;
import lev.philippov.originmvc.events.OrderConfirmationEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderConfirmationService {

    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final ApplicationEventPublisher publisher;

    public void requestMQConfirmation(Long orderId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGER_NAME,RabbitMQConfig.ROUTING_KEY, orderId.toString());
    }

    public void receive(byte[] bytes){
        String token = new String(bytes);
        logger.info("Received message: {}",token);

        if(token.matches("^[0-9]*_OK$")){
            Long orderId  = Long.parseLong(token.split("_")[0]);
            publisher.publishEvent(new OrderConfirmationEvent(orderId, OrderStatus.CONFIRMED));
        } else if (token.matches("^[0-9]*_NOT$")){
            Long orderId  = Long.parseLong(token.split("_")[0]);
            publisher.publishEvent(new OrderConfirmationEvent(orderId, OrderStatus.NOT_CONFIRMED));
        } else {
            throw new RuntimeException("Unknown token from RabbitMQ: " + token);
        }
    }

}
