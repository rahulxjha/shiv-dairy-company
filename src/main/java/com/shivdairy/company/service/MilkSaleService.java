package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;

import java.time.LocalDate;
import java.util.List;


public interface MilkSaleService {
    MilkSaleDetails saveMilkSaleDetails(MilkSaleRequestDTO milkSaleRequestDTO);
    List<MilkSaleDetails> getAllMilkSaleDetails();
    List<MilkSaleDetails> getAllMilkSaleDetails(String name, LocalDate startDate, LocalDate endDate);
}
