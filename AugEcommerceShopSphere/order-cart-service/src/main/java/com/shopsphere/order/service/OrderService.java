package com.shopsphere.order.service;

import com.shopsphere.order.client.CatalogServiceClient;
import com.shopsphere.order.dto.CheckoutRequest;
import com.shopsphere.order.dto.OrderPlacedEvent;
import com.shopsphere.order.dto.ProductDto;
import com.shopsphere.order.dto.StockReductionDto;
import com.shopsphere.order.entity.CartItem;
import com.shopsphere.order.entity.Order;
import com.shopsphere.order.entity.OrderItem;
import com.shopsphere.order.exception.ApiException;
import com.shopsphere.order.messaging.OrderEventPublisher;
import com.shopsphere.order.repository.CartItemRepository;
import com.shopsphere.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final CatalogServiceClient catalogServiceClient;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(CartItemRepository cartItemRepository,
                         OrderRepository orderRepository,
                         OrderEventPublisher orderEventPublisher,CatalogServiceClient catalogServiceClient) {
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.catalogServiceClient = catalogServiceClient;
    }


    @Transactional
    public Order checkout(Long userId,CheckoutRequest request){
        //fetch the user cart
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if(cartItems.isEmpty()){
            throw new ApiException("Cart is Empty ",HttpStatus.BAD_REQUEST);
        }





        Order order = new Order();
        order.setUserId(userId);
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        List<StockReductionDto> stockReductions = new ArrayList<>();

        for (CartItem cartItem: cartItems) {
           //live data from catalog service for details matching
            ProductDto liveProduct = catalogServiceClient.getProductById(cartItem.getProductId());


            //validate price
            if(liveProduct.getPrice().compareTo(cartItem.getPrice()) != 0) {

                //update the cart item to new Price
                cartItem.setPrice(liveProduct.getPrice());
                cartItem.setQuantity(liveProduct.getStockQuantity());
                cartItemRepository.save(cartItem);
                //abort the request
                throw new ApiException("The Price of " + cartItem.getProductName() + "  in your stock Changed. Plese review your cart ", HttpStatus.CONFLICT);
            }
                //validate the stock
                if(liveProduct.getStockQuantity() <cartItem.getQuantity()) {
                    throw new ApiException("Product " + liveProduct.getName() + " is out of stock!", HttpStatus.CONFLICT);
                }
                //now after the checks i will build the order

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProductId(cartItem.getProductId());
                orderItem.setProductName(cartItem.getProductName());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getPrice());
                order.getItems().add(orderItem);

                total = total.add(orderItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                stockReductions.add(new StockReductionDto(cartItem.getProductId(),cartItem.getQuantity()));

            }

           order.setTotalAmount(total);

            //reduce the stock via feign client and updte the order table
            catalogServiceClient.reduceStock(stockReductions);

            //save order and clear cart
            Order savedOrder = orderRepository.save(order);
            cartItemRepository.deleteByUserId(userId);

            //push order event for admin reporting service  maintaining order records
            orderEventPublisher.publishOrder(
                    new OrderPlacedEvent(savedOrder.getId(), savedOrder.getUserId(), savedOrder.getTotalAmount())
            );

        return savedOrder;

    }
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ApiException("Order not found", HttpStatus.NOT_FOUND));
    }

    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Called by admin-reporting-service via Feign client  when an admin updates an order's status: Packed / Shipped / Delivered / Cancelled.
    public Order updateStatus(Long id, String status) {
        Order order = getOrder(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
