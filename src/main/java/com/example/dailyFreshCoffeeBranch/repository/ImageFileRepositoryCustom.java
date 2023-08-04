package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.domain.ImageFile;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageFileRepositoryCustom {

    List<ImageFile> findAllByItemId(@Param("itemId") Long itemId);

    void deleteByItemId(@Param("itemId") Long itemId);

}