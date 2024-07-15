package com.shivdairy.company.service;

import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;


public interface MilkSaleService {
    MilkSaleDetails saveMilkSaleDetails(MilkSaleRequestDTO milkSaleRequestDTO);

}
