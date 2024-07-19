package com.shivdairy.company.service.impl;

import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.repository.MilkSaleRepository;
import com.shivdairy.company.service.MilkSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<MilkSaleDetails> getAllMilkSaleDetails() {
        return milkSaleRepository.findAll();
    }

    @Override
    public List<MilkSaleDetails> getAllMilkSaleDetails(String buyerName, LocalDate startDate, LocalDate endDate) {
        List<MilkSaleDetails> milkSaleDetails = milkSaleRepository.getMilkSaleDetails(buyerName, startDate, endDate);
        if (!milkSaleDetails.isEmpty()) {
            return milkSaleDetails.stream()
                    .filter(saleDetails -> saleDetails.getBuyerName().equalsIgnoreCase(buyerName))
                    .filter(saleDetails -> !saleDetails.getDate().isBefore(startDate) && !saleDetails.getDate().isAfter(endDate))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
