package com.shivdairy.company.controller;

import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.service.MilkSaleService;
import com.shivdairy.company.service.PdfService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.parse;

@Slf4j
@RestController
@RequestMapping(value = "public/api/v1")
public class MilkSaleApi {
    @Autowired
    private MilkSaleService milkSaleService;
    @Autowired
    private PdfService pdfService;

    @GetMapping("/getAllMilkSaleDetails")
    public ResponseEntity<BaseResponseDTO<List<MilkSaleDetails>>> getAllMilkSaleDetails(){
        log.info("Requesting for api/v1/getAllMilkSaleDetails");
        List<MilkSaleDetails> milkSaleDetails = milkSaleService.getAllMilkSaleDetails();
        BaseResponseDTO<List<MilkSaleDetails>> milkSaleDetailsResponse =
                new BaseResponseDTO<>(MilkConstant.MILK_SALE_DETAILS_FETCHED, milkSaleDetails);
        return ResponseEntity.ok(milkSaleDetailsResponse);
    }

    @GetMapping("/getAllMilkSaleDetailsFilter")
    public ResponseEntity<BaseResponseDTO<List<MilkSaleDetails>>> getAllMilkSaleDetails(@RequestParam Map<String, String> params){
        log.info("Requesting for api/v1/getAllMilkSaleDetails with RequestParam: {}", params);
        List<MilkSaleDetails> milkSaleDetailsFilter = milkSaleService.getAllMilkSaleDetails(params.get("buyerName"),
                parse(params.get("startDate")), parse(params.get("endDate")));
        BaseResponseDTO<List<MilkSaleDetails>> milkSaleDetailsFilterResponse =
                new BaseResponseDTO<>(MilkConstant.MILK_SALE_DETAILS_FETCHED_FILTER, milkSaleDetailsFilter);
        return ResponseEntity.ok(milkSaleDetailsFilterResponse);
    }

    @PostMapping("/calculateBuyerMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkSaleDetails>> calculateBuyerMilkProperty(@Valid @RequestBody MilkSaleRequestDTO milkSaleRequestDTO){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkSaleRequestDTO);
        MilkSaleDetails milkSaleDetails = milkSaleService.saveMilkSaleDetails(milkSaleRequestDTO);
        BaseResponseDTO<MilkSaleDetails> milkPropertyResponse =
                new BaseResponseDTO<>(MilkConstant.MILK_PROPERTIES_CALCULATED, milkSaleDetails);
        return ResponseEntity.ok(milkPropertyResponse);
    }

    @GetMapping("/generateMilkSaleDetails-pdf")
    public ResponseEntity<InputStreamResource> generatePdf() {
        ByteArrayInputStream bis = pdfService.generatePdfForMilkSaleDetails();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=milk-sale-details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
