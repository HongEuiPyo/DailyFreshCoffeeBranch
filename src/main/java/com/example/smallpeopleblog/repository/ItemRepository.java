package com.example.smallpeopleblog.repository;

import com.example.smallpeopleblog.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
