package com.shopsphere.order.messaging;

import com.shopsphere.order.config.RabbitMQConfig;
import com.shopsphere.order.dto.OrderPlacedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate){

        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrder(OrderPlacedEvent orderEvent) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE , RabbitMQConfig.ORDER_PLACED_BINDING_KEY,orderEvent);
    }
}
