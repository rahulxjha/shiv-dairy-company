package com.shivdairy.company.service.impl;

import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.dto.MilkType;
import com.shivdairy.company.exception.NoItemFoundException;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;
import com.shivdairy.company.repository.MilkRepository;
import com.shivdairy.company.service.MilkService;
import com.shivdairy.company.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MilkServiceImpl implements MilkService {
    @Autowired
    private MilkRepository milkRepository;
    private Double fatWeight;
    private Double snfPercent;
    private Double snfWeight;
    private Double fatRate;
    private Double snfRate;
    private Double fatAmount;
    private Double snfAmount;
    private Double theTotalPayAmount;

    @Override
    public MilkDetails calculateMilkProperty(MilkDetailsRequestDTO milkDetails) {
        log.info("inside MilkServiceImpl.calculateMilkProperty: {}", milkDetails);
        fatWeight = calculateFatWeight(milkDetails.getMilkWeight(), milkDetails.getFat());
        snfPercent = calculateSnfPercent(milkDetails.getClr(), milkDetails.getFat());
        snfWeight = calculateSnfWeight(milkDetails.getMilkWeight(), snfPercent);
        fatRate = calculateFatRate(milkDetails.getMilkRate());
        snfRate = calculateSnfRate(milkDetails.getMilkRate());
        fatAmount = calculateFatAmount(fatWeight , fatRate);
        snfAmount = calculateSnfAmount(snfWeight , snfRate);
        theTotalPayAmount = fatAmount + snfAmount;
        MilkDetails milkDetailsModel = getMilkDetailsModel(milkDetails);
        return milkRepository.save(milkDetailsModel);
    }

    @Override
    public MilkPaymentSummary getMilkPayment( String name, LocalDate startDate, LocalDate endDate) {
        List<MilkDetails> milkDetails = milkRepository.getMilkPayment(name, startDate, endDate);
        if (!milkDetails.isEmpty()) {
            List<MilkPaymentSummary.MilkPaymentDetails> milkPaymentDetails = milkDetails.stream()
                    .map(detail -> new MilkPaymentSummary.MilkPaymentDetails(detail.getDate(), detail.getMilkPayment(),
                            detail.getSupplier().getPaymentStatus()))
                    .collect(Collectors.toList());
            return new MilkPaymentSummary(milkPaymentDetails);
        } else throw new NoItemFoundException(String.format(MilkConstant.MILK_DETAILS_NOT_FOUND_EXCEPTION, name));
    }

    private Double calculateFatWeight(Double milkWeight, Double fat){
        log.info("inside MilkServiceImpl.calculateFatWeight: {} {}", milkWeight, fat);
        return round((milkWeight * fat / 1000.0));
    }

    private Double calculateSnfPercent(Double clr, Double fat){
        log.info("inside MilkServiceImpl.calculateSnfPercent: {} {}", clr, fat);
        return round((clr * 25) + 14 + fat * 2);
    }

    private Double calculateSnfWeight (Double milkWeight, Double snfPercent){
        log.info("inside MilkServiceImpl.calculateSnfWeight: {} {}", milkWeight, snfPercent);
        return round((milkWeight * snfPercent) / 10000);
    }

    private Double calculateFatRate(Double milkRate){
        log.info("inside MilkServiceImpl.calculateFatRate: {}", milkRate);
        return round((milkRate * 60 / 6.5));
    }

    private Double calculateSnfRate(Double milkRate){
        log.info("inside MilkServiceImpl.calculateSnfRate: {}", milkRate);
        return round((milkRate * 40 / 8.5));
    }

    private Double calculateFatAmount(Double fatWeight, Double fatRate){
        log.info("inside MilkServiceImpl.calculateFatAmount: {} {}", fatRate, fatWeight);
        return round(fatWeight * fatRate);
    }

    private Double calculateSnfAmount(Double snfWeight, Double snfRate){
        log.info("inside MilkServiceImpl.calculateSnfAmount: {} {}", snfWeight, snfRate);
        return round(snfWeight * snfRate);
    }

    private MilkDetails getMilkDetailsModel (MilkDetailsRequestDTO milkDetailsRequestDTO ) {
        MilkDetails milkDetailsModel = new MilkDetails();
        milkDetailsModel.setMilkType(MilkType.BUFFALO);
        milkDetailsModel.setFatWeight(fatWeight);
        milkDetailsModel.setSnfPercent(snfPercent);
        milkDetailsModel.setSnfWeight(snfWeight);
        milkDetailsModel.setFatRate(fatRate);
        milkDetailsModel.setSnfRate(snfRate);
        milkDetailsModel.setFatAmount(fatAmount);
        milkDetailsModel.setSnfAmount(snfAmount);
        milkDetailsModel.setMilkPayment(theTotalPayAmount);
        milkDetailsModel.setMilkRate(milkDetailsRequestDTO.getMilkRate());
        milkDetailsModel.setFat(milkDetailsRequestDTO.getFat());
        milkDetailsModel.setClr(milkDetailsRequestDTO.getClr());
        milkDetailsModel.setMilkWeight(milkDetailsRequestDTO.getMilkWeight());
        milkDetailsModel.setName(milkDetailsRequestDTO.getName());
        milkDetailsModel.setDate(DateTimeUtil.date);
        return milkDetailsModel;
    }

    private Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
