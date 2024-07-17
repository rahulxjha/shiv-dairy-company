package com.shivdairy.company.controller;

import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.service.MilkSaleService;
import com.shivdairy.company.service.MilkService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "public/api/v1")
public class MilkSaleApi {
    @Autowired
    private MilkSaleService milkService;

    @PostMapping("/calculateBuyerMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkSaleDetails>> calculateMilkProperty(@Valid @RequestBody MilkSaleRequestDTO milkSaleRequestDTO){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkSaleRequestDTO);
        MilkSaleDetails milkSaleDetails = milkService.saveMilkSaleDetails(milkSaleRequestDTO);
        BaseResponseDTO<MilkSaleDetails> milkPropertyResponse =
                new BaseResponseDTO<>(MilkConstant.MILK_PROPERTIES_CALCULATED, milkSaleDetails);
        return ResponseEntity.ok(milkPropertyResponse);
    }
}
