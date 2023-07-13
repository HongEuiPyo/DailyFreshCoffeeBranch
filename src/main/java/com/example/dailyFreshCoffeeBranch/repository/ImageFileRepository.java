package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    @Query("SELECT i FROM ImageFile i WHERE i.item.id = :itemId")
    List<ImageFile> findAllByItemId(@Param("itemId") Long itemId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ImageFile i WHERE i.item.id = :itemId")
    void deleteByItemId(@Param("itemId") Long itemId);
}