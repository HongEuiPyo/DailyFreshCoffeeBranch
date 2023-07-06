package com.example.smallpeopleblog.repository;

import com.example.smallpeopleblog.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.member.id = :memberId ORDER BY p.modifiedTime desc")
    List<Payment> findByMemberId(@Param("memberId") Long memberId);
}
