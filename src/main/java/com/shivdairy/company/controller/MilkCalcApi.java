package com.shivdairy.company.controller;

import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;
import com.shivdairy.company.service.MilkService;
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
import java.util.Map;

import static java.time.LocalDate.*;

@Slf4j
@RestController
@RequestMapping(value = "public/api/v1")
public class MilkCalcApi {
    @Autowired
    private MilkService milkService;
    @Autowired
    private PdfService pdfService;

    @PostMapping("/calculateSupplierMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkDetails>> calculateMilkProperty(@Valid @RequestBody MilkDetailsRequestDTO milkDetailsRequestDTO){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkDetailsRequestDTO);
        MilkDetails milkDetails = milkService.saveMilkDetails(milkDetailsRequestDTO);
        BaseResponseDTO<MilkDetails> milkPropertyResponse = new BaseResponseDTO<>(MilkConstant.MILK_PROPERTIES_CALCULATED, milkDetails);
        return ResponseEntity.ok(milkPropertyResponse);
    }

    @GetMapping("/getMilkPayment")
    public ResponseEntity<BaseResponseDTO<MilkPaymentSummary>> getMilkPayment(@RequestParam Map<String, String> params){
        log.info("Requesting for api/v1/calculateMilkProperty with RequestParam: {}", params);
        MilkPaymentSummary theMilkPayment = milkService.getMilkPayment(params.get("name"),
                parse(params.get("startDate")), parse(params.get("endDate")));
        BaseResponseDTO<MilkPaymentSummary> theMilkPaymentResponse = new BaseResponseDTO<>(MilkConstant.MILK_PAYMENT_FETCHED , theMilkPayment);
        return ResponseEntity.ok(theMilkPaymentResponse);
    }

    @GetMapping("/generateMilkDetails-pdf")
    public ResponseEntity<InputStreamResource> generatePdfForMilkDetails() {
        ByteArrayInputStream bis = pdfService.generatePdfForMilkDetails();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=milk-details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
