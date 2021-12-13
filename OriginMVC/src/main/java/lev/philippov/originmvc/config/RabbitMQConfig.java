package lev.philippov.originmvc.config;

import lev.philippov.originmvc.services.rabbitmq.OrderConfirmationService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig implements CommandLineRunner {

    public final static String QUEUE_NAME = "order_confirmation";
    public final static String EXCHANGER_NAME = "order_confirmation";
    public final static String ROUTING_KEY = "k_server";
    public final static String INVERSE_ROUTING_KEY = "k_client";

    @Override
    public void run(String... args) throws Exception {
//        TopicExchange topicExchange = new TopicExchange(EXCHANGER_NAME,false,true);
//        Queue queue = new Queue(QUEUE_NAME, false, false, true);
//        BindingBuilder.bind(queue).to(topicExchange).with(INVERSE_ROUTING_KEY);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGER_NAME,false,true);
    }

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME,false,false,true);
    }

    @Bean
    public Binding bind(TopicExchange topicExchange, Queue queue){
        return BindingBuilder.bind(queue).to(topicExchange).with(INVERSE_ROUTING_KEY);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(OrderConfirmationService service) {
        return new MessageListenerAdapter(service, "receive");
    }

    @Bean
    SimpleMessageListenerContainer containerForTopic(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue());
        container.setPrefetchCount(1);
        MessageListenerAdapter receiver = new MessageListenerAdapter(listenerAdapter, "receive");
        container.setMessageListener(receiver);
        return container;
    }

}
