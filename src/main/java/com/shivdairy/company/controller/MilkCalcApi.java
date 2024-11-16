package com.shivdairy.company.controller;

import com.lowagie.text.DocumentException;
import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkDetailsRequestDTO;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkPaymentSummary;
import com.shivdairy.company.service.MilkService;
import com.shivdairy.company.service.PdfService;
import com.shivdairy.company.utils.DateTimeUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
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
    @Autowired
    private Environment environmentProperties;

    @PostMapping("calculateMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkDetails>> calculateMilkProperty(@Valid @RequestBody MilkDetailsRequestDTO milkDetailsRequestDTO){
        try {
            log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkDetailsRequestDTO);
            MilkDetails milkDetails = milkService.saveMilkDetails(milkDetailsRequestDTO);
            BaseResponseDTO<MilkDetails> milkPropertyResponse = new BaseResponseDTO<>(MilkConstant.MILK_PROPERTIES_CALCULATED, milkDetails);
            return ResponseEntity.ok(milkPropertyResponse);
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("milk.details.calculation.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environmentProperties.getProperty("milk.details.calculation.error") + e.getMessage(), e);
        }

    }

    @GetMapping("milkPayment")
    public ResponseEntity<BaseResponseDTO<MilkPaymentSummary>> getMilkPayment(@RequestParam Map<String, String> params){
        try {
            log.info("Requesting for api/v1/calculateMilkProperty with RequestParam: {}", params);
            MilkPaymentSummary theMilkPayment = milkService.getMilkPayment(params.get("name"),
                    parse(params.get("startDate")), parse(params.get("endDate")));
            BaseResponseDTO<MilkPaymentSummary> theMilkPaymentResponse = new BaseResponseDTO<>(MilkConstant.MILK_PAYMENT_FETCHED , theMilkPayment);
            return ResponseEntity.ok(theMilkPaymentResponse);
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("milk.details.get.payment.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environmentProperties.getProperty("milk.details.get.payment.error") + e.getMessage(), e)
        }

    }

    @GetMapping("milkDetailsPdf")
    public ResponseEntity<InputStreamResource> generatePdfForMilkDetails() throws DocumentException {
        try {
            ByteArrayInputStream bis = pdfService.generatePdfForMilkDetails();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=milk-details.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("shiv.dairy.generate.pdf.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environmentProperties.getProperty("shiv.dairy.generate.pdf.error") + e.getMessage(), e);
        }

    }

    @GetMapping("milkDetailsPdfByName")
    public ResponseEntity<InputStreamResource> generatePdfForMilkDetails(@RequestParam Map<String, String> params) throws DocumentException {
        try {
            LocalDate effectiveDate = LocalDate.parse(params.get("effectiveDate"), DateTimeUtil.dateFormatter);
            LocalDate endDate = LocalDate.parse(params.get("endDate"), DateTimeUtil.dateFormatter);
            ByteArrayInputStream bis = pdfService.generatePdfForMilkDetailsByName(params.get("supplierName"),
                    effectiveDate, endDate);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename="+ params.get("supplierName") + "-milk-details.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("shiv.dairy.generate.pdf.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environmentProperties.getProperty("shiv.dairy.generate.pdf.error") + e.getMessage(), e);
        }

    }
}
