package com.shivdairy.company.controller;

import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.service.MilkService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class MilkCalcApi {
    @Autowired
    private MilkService milkService;
    private static final String TOTAL_PAYMENT_CALCULATED = "Total payment calculated successfully.";

    @GetMapping("/totalPayAmount")
    public ResponseEntity<BaseResponseDTO<Double>> getTotalPayAmount(@Valid @RequestBody MilkDetailsRequestDTO milkDetails){
        log.info("Requesting for api/v1/totalPayAmount with RequestBody: {}", milkDetails);
        Double theTotalPayAmount = milkService.totalPayAmount(milkDetails.getMilkWeight(), milkDetails.getFat(), milkDetails.getClr(), milkDetails.getMilkRate());
        BaseResponseDTO<Double> totalPayAmountResponse = new BaseResponseDTO<>(TOTAL_PAYMENT_CALCULATED, theTotalPayAmount);
        return ResponseEntity.ok(totalPayAmountResponse);
    }
}
