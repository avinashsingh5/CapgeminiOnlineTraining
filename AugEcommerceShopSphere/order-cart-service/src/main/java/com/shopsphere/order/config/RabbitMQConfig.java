package com.shopsphere.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public  static final String ORDER_EXCHANGE = "order.exchange";

    public static final String ORDER_NOTIFICATION_QUEUE =   "order.notification.queue";

    public static final String ORDER_PLACED_BINDING_KEY = "order.placed";

    //exchange
    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_EXCHANGE,true,false);
    }

    //Queue
    @Bean
    public Queue orderNotificationQueue(){
        return new Queue(ORDER_NOTIFICATION_QUEUE,true);
    }

    //Binding
    @Bean
    public Binding orderNotificationBinding( Queue orderNotificationQueue,TopicExchange  orderExchange   ){
                return BindingBuilder.bind(orderNotificationQueue)
                        .to(orderExchange)
                        .with(ORDER_PLACED_BINDING_KEY);
    }

    //json convertor
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
