package lev.philippov;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Server {
    private final String EXCHANGE_NAME  = "order_confirmation";
    private final String INCOMING_ROUTING_KEY  = "k_server";
    private final String ROUTING_KEY  = "k_client";
//    private final String QUEUE_NAME = "order_confirmation";

    Scanner scanner;

    public Server() {
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        String queue = channel.queueDeclare().getQueue();
        channel.basicQos(1);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC,false,true,null);
        channel.queueBind(queue,EXCHANGE_NAME,INCOMING_ROUTING_KEY);

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String orderId = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("Confirming order number : " + orderId);
            System.out.println("Enter 'Y' to confirm.");
            if(scanner.next().equalsIgnoreCase("Y")) {
                System.out.println("Applied!");
                channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,null,(orderId+"_OK").getBytes(StandardCharsets.UTF_8));
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            } else {
                channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,null,(orderId+"_NOT").getBytes(StandardCharsets.UTF_8));
                channel.basicNack(message.getEnvelope().getDeliveryTag(),false,false);
            }
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("Cancelled.");
        };

        channel.basicConsume(queue,false,deliverCallback,cancelCallback);
    }
}
