package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;

import java.time.LocalDate;

public interface MilkService {
    MilkDetails calculateMilkProperty(MilkDetailsRequestDTO milkDetails);
    MilkPaymentSummary getMilkPayment(String name, LocalDate startDate, LocalDate endDate);
}
