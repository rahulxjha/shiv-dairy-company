package com.shivdairy.company.repository;

import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkSaleDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public interface MilkRepository extends JpaRepository<MilkDetails, String> {
    @Query("SELECT m FROM MilkDetails m WHERE m.name LIKE %:name% AND m.date BETWEEN :startDate AND :endDate")
    List<MilkDetails> getMilkPayment(@Param("name") String name, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT m FROM MilkDetails m WHERE m.name LIKE %:supplierName% AND m.date BETWEEN :effectiveDate " +
            "AND " +
            ":endDate")
    List<MilkDetails> getMilkDetails(@Param("supplierName") String supplierName, @Param("effectiveDate") LocalDate effectiveDate,
                                         @Param("endDate") LocalDate endDate);

}
