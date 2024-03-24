package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.model.MilkDetails;

import java.util.List;

public interface MilkService {
    MilkDetails calculateMilkProperty(MilkDetailsRequestDTO milkDetails);
    List<Double> getMilkPayment( String name);
}
