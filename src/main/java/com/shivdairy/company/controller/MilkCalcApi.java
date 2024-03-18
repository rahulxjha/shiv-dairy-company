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

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class MilkCalcApi {
    @Autowired
    private MilkService milkService;
    private static final String MILK_PROPERTIES_CALCULATED = "Milk properties calculated and saved successfully.";

    @GetMapping("/calculateMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkDetails>> getTotalPayAmount(@Valid @RequestBody MilkDetailsRequestDTO milkDetailsRequestDTO){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkDetailsRequestDTO);
        MilkDetails milkDetails = milkService.calculateMilkProperty(milkDetailsRequestDTO);
        BaseResponseDTO<MilkDetails> milkPropertyResponse = new BaseResponseDTO<>(MILK_PROPERTIES_CALCULATED, milkDetails);
        return ResponseEntity.ok(milkPropertyResponse);
    }
}
