package com.example.smallpeopleblog.repository;

import com.example.smallpeopleblog.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.id = :cartItemId AND c.cart.id = :cartId")
    Optional<CartItem> findByIdAndCartId(@Param("cartItemId") Long cartItemId, @Param("cartId") Long cartId);

}