package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkProperty;
import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;
import com.shivdairy.company.model.MilkSaleDetails;

import java.time.LocalDate;

public interface MilkService {
    MilkDetails saveMilkDetails(MilkProperty milkProperty);
    MilkPaymentSummary getMilkPayment(String name, LocalDate startDate, LocalDate endDate);
}
