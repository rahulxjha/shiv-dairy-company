package com.shivdairy.company.controller;

import com.shivdairy.company.constant.MilkConstant;
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

import static java.time.LocalDate.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class MilkCalcApi {
    @Autowired
    private MilkService milkService;

    @GetMapping("/calculateMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkDetails>> getTotalPayAmount(@Valid @RequestBody MilkDetailsRequestDTO milkDetailsRequestDTO){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkDetailsRequestDTO);
        MilkDetails milkDetails = milkService.calculateMilkProperty(milkDetailsRequestDTO);
        BaseResponseDTO<MilkDetails> milkPropertyResponse = new BaseResponseDTO<>(MilkConstant.MILK_PROPERTIES_CALCULATED, milkDetails);
        return ResponseEntity.ok(milkPropertyResponse);
    }

    @GetMapping("/getMilkPayment")
    public ResponseEntity<BaseResponseDTO<List<Double>>> getMilkPayment(@RequestParam Map<String, String> params){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestParam: {}", params);
        List<Double> theMilkPayment = milkService.getMilkPayment(params.get("name"),
                parse(params.get("startDate")), parse(params.get("endDate")));
        BaseResponseDTO<List<Double>> theMilkPaymentResponse = new BaseResponseDTO<>(MilkConstant.MILK_PAYMENT_FETCHED , theMilkPayment);
        return ResponseEntity.ok(theMilkPaymentResponse);
    }
}
