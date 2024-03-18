package com.shivdairy.company.service.impl;

import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.dto.MilkType;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.service.MilkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MilkServiceImpl implements MilkService {
    private Double fatKg;
    private Double snfPercent;
    private Double snfKg;
    private Double fatRate;
    private Double snfRate;
    private Double fatAmount;
    private Double snfAmount;
    private Double theTotalPayAmount;

    @Override
    public MilkDetails calculateMilkProperty(MilkDetailsRequestDTO milkDetails) {
        log.info("inside MilkServiceImpl.calculateMilkProperty: {}", milkDetails);
        fatKg = calculateFatKg(milkDetails.getMilkWeight(), milkDetails.getFat());
        snfPercent = calculateSnfPercent(milkDetails.getClr(), milkDetails.getFat());
        snfKg = calculateSnfKg(milkDetails.getMilkWeight(), snfPercent);
        fatRate = calculateFatRate(milkDetails.getMilkRate());
        snfRate = calculateSnfRate(milkDetails.getMilkRate());
        fatAmount = calculateFatAmount(fatKg, fatRate);
        snfAmount = calculateSnfAmount(snfKg, snfRate);
        theTotalPayAmount = fatAmount + snfAmount;
        MilkDetails milkDetailsModel = getMilkDetailsModel(milkDetails);
        return milkDetailsModel;
    }

    private MilkDetails getMilkDetailsModel (MilkDetailsRequestDTO milkDetails) {
        MilkDetails milkDetailsModel = new MilkDetails();
        milkDetailsModel.setMilkType(MilkType.BUFFALO);
        milkDetailsModel.setFatKg(fatKg);
        milkDetailsModel.setSnfPercent(snfPercent);
        milkDetailsModel.setSnfKg(snfKg);
        milkDetailsModel.setFatRate(fatRate);
        milkDetailsModel.setSnfRate(snfRate);
        milkDetailsModel.setFatAmount(fatAmount);
        milkDetailsModel.setSnfAmount(snfAmount);
        milkDetailsModel.setMilkPayment(theTotalPayAmount);
        milkDetailsModel.setMilkRate(milkDetails.getMilkRate());
        milkDetailsModel.setFat(milkDetails.getFat());
        milkDetailsModel.setClr(milkDetails.getClr());
        milkDetailsModel.setMilkWeight(milkDetails.getMilkWeight());
        return milkDetailsModel;
    }

    private Double calculateFatKg(Double milkWeight, Double fat){
        log.info("inside MilkServiceImpl.calculateFatKg: {} {}", milkWeight, fat);
        return (milkWeight * fat) / 1000;
    }

    private Double calculateSnfPercent(Double clr, Double fat){
        log.info("inside MilkServiceImpl.calculateSnfPercent: {} {}", clr, fat);
        return (clr * 25) + 14 + fat * 2;
    }

    private Double calculateSnfKg(Double milkWeight, Double snfPercent){
        log.info("inside MilkServiceImpl.calculateSnfKg: {} {}", milkWeight, snfPercent);
        return (milkWeight * snfPercent) / 10000;
    }

    private Double calculateFatRate(Double milkRate){
        log.info("inside MilkServiceImpl.calculateFatRate: {}", milkRate);
        return (milkRate * 60) / 6.5;
    }

    private Double calculateSnfRate(Double milkRate){
        log.info("inside MilkServiceImpl.calculateSnfRate: {}", milkRate);
        return (milkRate * 40) / 8.5;
    }

    private Double calculateFatAmount(Double fatKg, Double fatRate){
        log.info("inside MilkServiceImpl.calculateFatAmount: {} {}", fatRate, fatKg);
        return fatKg * fatRate;
    }

    private Double calculateSnfAmount(Double snfKg, Double snfRate){
        log.info("inside MilkServiceImpl.calculateSnfAmount: {} {}", snfKg, snfRate);
        return snfKg * snfRate;
    }
}
