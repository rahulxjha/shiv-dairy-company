package com.shivdairy.company.repository;

import com.shivdairy.company.model.MilkDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface MilkRepository extends JpaRepository<MilkDetails, String> {
    @Query("SELECT m FROM MilkDetails m WHERE m.name LIKE %:name%")
    List<MilkDetails> getMilkPayment( @Param("name") String name);

}
