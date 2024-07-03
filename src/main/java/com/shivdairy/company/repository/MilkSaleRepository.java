package com.shivdairy.company.repository;

import com.shivdairy.company.model.MilkSaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilkSaleRepository extends JpaRepository<MilkSaleDetails, Long> {
}
