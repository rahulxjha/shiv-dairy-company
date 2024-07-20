package com.shivdairy.company.service.impl;

import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.MilkProperty;
import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.dto.MilkType;
import com.shivdairy.company.exception.NoItemFoundException;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;
import com.shivdairy.company.model.MilkSaleDetails;
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

    public void calculateMilkProperty(MilkProperty milkProperty) {
        log.info("inside MilkServiceImpl.calculateMilkProperty: {}", milkProperty);
        fatWeight = calculateFatWeight(milkProperty.getMilkWeight(), milkProperty.getFat());
        snfPercent = calculateSnfPercent(milkProperty.getClr(), milkProperty.getFat());
        snfWeight = calculateSnfWeight(milkProperty.getMilkWeight(), snfPercent);
        fatRate = calculateFatRate(milkProperty.getMilkRate());
        snfRate = calculateSnfRate(milkProperty.getMilkRate());
        fatAmount = calculateFatAmount(fatWeight , fatRate);
        snfAmount = calculateSnfAmount(snfWeight , snfRate);
        theTotalPayAmount = round(fatAmount + snfAmount);
    }

    @Override
    public MilkDetails saveMilkDetails(MilkProperty milkProperty){
        calculateMilkProperty(milkProperty);
        MilkDetails milkDetailsModel = getMilkDetailsModel(milkProperty);
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

    @Override
    public List<MilkDetails> getAllMilkDetails() {
        List<MilkDetails> milkdetailsList = milkRepository.findAll();
        if (!milkdetailsList.isEmpty()) return milkdetailsList;
        else throw new NoItemFoundException("There is no milk details present in the database.");
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

    private MilkDetails getMilkDetailsModel (MilkProperty milkProperty ) {
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
        milkDetailsModel.setMilkRate(milkProperty.getMilkRate());
        milkDetailsModel.setFat(milkProperty.getFat());
        milkDetailsModel.setClr(milkProperty.getClr());
        milkDetailsModel.setMilkWeight(milkProperty.getMilkWeight());
        milkDetailsModel.setName(milkProperty.getName());
        milkDetailsModel.setDate(DateTimeUtil.date);
        return milkDetailsModel;
    }

    MilkSaleDetails getMilkSaleDetailsModel(MilkSaleRequestDTO milkSaleRequestDTO){
        MilkSaleDetails milkSaleDetails = new MilkSaleDetails();
        milkSaleDetails.setBuyerName(milkSaleRequestDTO.getBuyerMilkDetails().getName());
        milkSaleDetails.setPaymentStatus(milkSaleRequestDTO.getBuyerMilkDetails().getPaymentStatus());
        milkSaleDetails.setBuyerMilkWeight(milkSaleRequestDTO.getBuyerMilkDetails().getMilkWeight());
        milkSaleDetails.setBuyerFat(milkSaleRequestDTO.getBuyerMilkDetails().getFat());
        milkSaleDetails.setFatWeight(fatWeight);
        milkSaleDetails.setFatRate(fatRate);
        milkSaleDetails.setFatAmount(fatAmount);
        milkSaleDetails.setSnfWeight(snfWeight);
        milkSaleDetails.setSnfPercent(snfPercent);
        milkSaleDetails.setSnfRate(snfRate);
        milkSaleDetails.setSnfAmount(snfAmount);
        milkSaleDetails.setBuyerClr(milkSaleRequestDTO.getBuyerMilkDetails().getClr());
        milkSaleDetails.setMilkRate(milkSaleRequestDTO.getBuyerMilkDetails().getMilkRate());
        milkSaleDetails.setDate(DateTimeUtil.date);
        milkSaleDetails.setSellerName(milkSaleRequestDTO.getSellerMilkDetails().getName());
        milkSaleDetails.setMilkWeight(milkSaleRequestDTO.getSellerMilkDetails().getMilkWeight());
        milkSaleDetails.setFat(milkSaleRequestDTO.getSellerMilkDetails().getFat());
        milkSaleDetails.setClr(milkSaleRequestDTO.getSellerMilkDetails().getClr());
        milkSaleDetails.setMilkPayment(theTotalPayAmount);
        return milkSaleDetails;
    }

    private Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
