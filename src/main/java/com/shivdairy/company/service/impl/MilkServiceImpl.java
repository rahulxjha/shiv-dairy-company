package com.shivdairy.company.service.impl;

import com.shivdairy.company.service.MilkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MilkServiceImpl implements MilkService {
    @Override
    public Double totalPayAmount(Double milkWeight, Double fat, Double clr, Double milkRate) {
        log.info("inside MilkServiceImpl.totalPayAmount: {} {} {}", milkWeight, fat, clr);
        Double fatKg = calculateFatKg(milkWeight, fat);
        Double snfPercent = calculateSnfPercent(clr, fat);
        Double snfKg = calculateSnfKg(milkWeight, snfPercent);
        Double fatRate = calculateFatRate(milkRate);
        Double snfRate = calculateSnfRate(milkRate);
        Double fatAmount = calculateFatAmount(fatKg, fatRate);
        Double snfAmount = calculateSnfAmount(snfKg, snfRate);
        return fatAmount + snfAmount;
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
        log.info("inside MilkServiceImpl.calculateFatAmount: {} {}", snfKg, snfRate);
        return snfKg * snfRate;
    }
}
