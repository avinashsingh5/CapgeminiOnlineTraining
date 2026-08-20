package com.shopsphere.order.service;

import com.shopsphere.order.client.CatalogServiceClient;
import com.shopsphere.order.dto.AddToCartRequest;
import com.shopsphere.order.dto.ProductDto;
import com.shopsphere.order.entity.CartItem;
import com.shopsphere.order.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CatalogServiceClient catalogServiceClient;

    public CartService(CartItemRepository cartItemRepository,CatalogServiceClient catalogServiceClient) {

        this.cartItemRepository = cartItemRepository;
        this.catalogServiceClient = catalogServiceClient;
    }

    /** Adds a product to the cart, or increases the quantity if it's already there. */
    public CartItem addOrUpdateItem(Long userId, AddToCartRequest request) {

        //fetching product form the catlalog service
        ProductDto product = catalogServiceClient.getProductById(request.getProductId());


        //find existing cart item or create a new one
        CartItem item = cartItemRepository.
                findByUserIdAndProductId(userId, request.getProductId())
                .orElse(new CartItem());

        //populate the CartItem from trusted calls i am not taking details of CartItem From user

        item.setUserId(userId);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setPrice(product.getPrice());

        //safely increment the quantity

        if(item.getId() != null){
            item.setQuantity(item.getQuantity()+ request.getQuantity());
        }else{
            item.setQuantity(request.getQuantity());
        }

        return cartItemRepository.save(item);

    }

    public List<CartItem> getCart(Long userId) {

        return cartItemRepository.findByUserId(userId);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
