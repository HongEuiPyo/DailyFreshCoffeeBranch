package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.entity.ImageFile;
import com.example.dailyFreshCoffeeBranch.repository.impl.ImageFileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long>, ImageFileRepositoryCustom {

}