package com.example.smallpeopleblog.repository;

import com.example.smallpeopleblog.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.member.id = :memberId ORDER BY c.modifiedTime desc")
    Optional<Cart> findByMemberId(@Param("memberId") Long memberId);

}