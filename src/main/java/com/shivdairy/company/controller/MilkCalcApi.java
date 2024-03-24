package com.shivdairy.company.controller;

import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.service.MilkService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class MilkCalcApi {
    @Autowired
    private MilkService milkService;
    private static final String MILK_PROPERTIES_CALCULATED = "Milk properties calculated and saved successfully.";
    private static final String MILK_PAYMENT_FETCHED = "Milk payment fetched successfully.";

    @GetMapping("/calculateMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkDetails>> getTotalPayAmount(@Valid @RequestBody MilkDetailsRequestDTO milkDetailsRequestDTO){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkDetailsRequestDTO);
        MilkDetails milkDetails = milkService.calculateMilkProperty(milkDetailsRequestDTO);
        BaseResponseDTO<MilkDetails> milkPropertyResponse = new BaseResponseDTO<>(MILK_PROPERTIES_CALCULATED, milkDetails);
        return ResponseEntity.ok(milkPropertyResponse);
    }

    @GetMapping("/getMilkPayment")
    public ResponseEntity<BaseResponseDTO<List<Double>>> getMilkPayment(@RequestParam Map<String, String> params){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestParam: {}", params);
        List<Double> theMilkPayment = milkService.getMilkPayment(params.get("name"));
        BaseResponseDTO<List<Double>> theMilkPaymentResponse = new BaseResponseDTO<>(MILK_PAYMENT_FETCHED , theMilkPayment);
        return ResponseEntity.ok(theMilkPaymentResponse);
    }
}
