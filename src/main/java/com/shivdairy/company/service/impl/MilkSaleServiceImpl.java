package com.shivdairy.company.service.impl;

import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.repository.MilkSaleRepository;
import com.shivdairy.company.service.MilkSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilkSaleServiceImpl implements MilkSaleService {
    @Autowired
    private MilkServiceImpl milkService;
    @Autowired
    private MilkSaleRepository milkSaleRepository;

    @Override
    public MilkSaleDetails saveMilkSaleDetails(MilkSaleRequestDTO milkSaleRequestDTO) {
        milkService.calculateMilkProperty(milkSaleRequestDTO.getBuyerMilkDetails());
        MilkSaleDetails milkSaleDetails = milkService.getMilkSaleDetailsModel(milkSaleRequestDTO);
        return milkSaleRepository.save(milkSaleDetails);
    }
}
