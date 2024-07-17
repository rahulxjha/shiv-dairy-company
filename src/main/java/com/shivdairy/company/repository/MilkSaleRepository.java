package com.shivdairy.company.repository;

import com.shivdairy.company.model.MilkSaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MilkSaleRepository extends JpaRepository<MilkSaleDetails, Long> {
    @Query("SELECT m FROM MilkSaleDetails m WHERE m.buyerName LIKE %:buyerName% AND m.date BETWEEN :startDate AND :endDate")
    List<MilkSaleDetails> getMilkSaleDetails(@Param("buyerName") String buyerName, @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);
}
