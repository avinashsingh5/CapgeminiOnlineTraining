package com.shopsphere.order.repository;

import com.shopsphere.order.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //give me all cart item belonging to the user
    List<CartItem> findByUserId(Long userId);
    //give me all cart item belonging to the user with this product Id
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserId(Long userId);
}
