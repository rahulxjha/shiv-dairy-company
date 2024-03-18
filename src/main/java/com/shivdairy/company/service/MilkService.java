package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.model.MilkDetails;

public interface MilkService {
    MilkDetails calculateMilkProperty(MilkDetailsRequestDTO milkDetails);
}
