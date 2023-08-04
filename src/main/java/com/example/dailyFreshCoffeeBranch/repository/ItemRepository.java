package com.example.dailyFreshCoffeeBranch.repository;

import com.example.dailyFreshCoffeeBranch.constant.ItemCategory;
import com.example.dailyFreshCoffeeBranch.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    @Query("SELECT i FROM Item i WHERE i.title LIKE %:searchKeyword% AND i.itemCategory = :searchType1")
    Page<Item> findByTitleLikeAndItemCategory(@Param("searchKeyword") String searchKeyword, @Param("searchType1") ItemCategory searchType1, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.title LIKE %:searchKeyword%")
    Page<Item> findByTitleLike(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.itemCategory = :searchType1")
    Page<Item> findByItemCategory(@Param("searchType1") ItemCategory searchType1, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.summary LIKE %:searchKeyword% AND i.itemCategory = :searchType1")
    Page<Item> findBySummaryLikeAndItemCategory(@Param("searchKeyword") String searchKeyword, @Param("searchType1") ItemCategory searchCategory1, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.title LIKE %:searchKeyword% OR i.summary LIKE %:searchKeyword% AND i.itemCategory = :searchType1")
    Page<Item> findByTitleLikeOrSummaryLikeAndItemCategory(@Param("searchKeyword") String searchKeyword, @Param("searchType1") ItemCategory searchCategory1, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.summary LIKE %:searchKeyword%")
    Page<Item> findBySummaryLike(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.title LIKE %:searchKeyword% OR i.summary LIKE %:searchKeyword%")
    Page<Item> findByTitleLikeOrSummaryLike(@Param("searchKeyword") String searchKeyword, Pageable pageable);

}