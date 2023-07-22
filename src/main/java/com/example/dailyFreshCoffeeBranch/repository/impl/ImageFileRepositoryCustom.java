package com.example.dailyFreshCoffeeBranch.repository.impl;

import com.example.dailyFreshCoffeeBranch.entity.ImageFile;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageFileRepositoryCustom {

    List<ImageFile> findAllByItemId(@Param("itemId") Long itemId);

    void deleteByItemId(@Param("itemId") Long itemId);

}