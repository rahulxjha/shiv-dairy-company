package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkProperty;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;

import java.time.LocalDate;
import java.util.List;

public interface MilkService {
    MilkDetails saveMilkDetails(MilkProperty milkProperty);
    MilkPaymentSummary getMilkPayment(String name, LocalDate startDate, LocalDate endDate);
    List<MilkDetails> getAllMilkDetails();
    List<MilkDetails> getAllMilkDetailsByName(String supplierName, LocalDate effectiveDate, LocalDate endDate);
    Double round(Double value);
}
