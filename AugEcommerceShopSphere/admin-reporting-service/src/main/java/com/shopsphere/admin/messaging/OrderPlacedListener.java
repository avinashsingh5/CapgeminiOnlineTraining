package com.shopsphere.admin.messaging;

import com.shopsphere.admin.config.RabbitMQConfig;
import com.shopsphere.admin.dto.OrderPlacedEvent;
import com.shopsphere.admin.entity.OrderRecord;
import com.shopsphere.admin.repository.OrderRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * REQUIRED BY SPEC: consumes the ORDER_PLACED event published by
 * order-cart-service on checkout, logs it, and stores a lightweight local
 * copy so the admin dashboard/report endpoints have data to work with.
 */
@Component
public class OrderPlacedListener {

    private static final Logger log = LoggerFactory.getLogger(OrderPlacedListener.class);

    private final OrderRecordRepository orderRecordRepository;

    public OrderPlacedListener(OrderRecordRepository orderRecordRepository) {
        this.orderRecordRepository = orderRecordRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("Received ORDER_PLACED event: orderId={}, userId={}, totalAmount={}",
                event.getOrderId(), event.getUserId(), event.getTotalAmount());

        OrderRecord record = new OrderRecord();
        record.setOrderId(event.getOrderId());
        record.setUserId(event.getUserId());
        record.setTotalAmount(event.getTotalAmount());
        orderRecordRepository.save(record);
    }
}
