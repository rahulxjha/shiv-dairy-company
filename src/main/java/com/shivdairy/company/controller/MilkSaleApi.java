package com.shivdairy.company.controller;

import com.lowagie.text.DocumentException;
import com.shivdairy.company.constant.MilkConstant;
import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.MilkSaleRequestDTO;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.service.MilkSaleService;
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
    @Autowired
    private Environment environmentProperties;

    @GetMapping("/getAllMilkSaleDetails")
    public ResponseEntity<BaseResponseDTO<List<MilkSaleDetails>>> getAllMilkSaleDetails() {
        try {
            log.info("Requesting for api/v1/getAllMilkSaleDetails");
            List<MilkSaleDetails> milkSaleDetails = milkSaleService.getAllMilkSaleDetails();
            BaseResponseDTO<List<MilkSaleDetails>> milkSaleDetailsResponse =
                    new BaseResponseDTO<>(MilkConstant.MILK_SALE_DETAILS_FETCHED, milkSaleDetails);
            return new ResponseEntity<>(milkSaleDetailsResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("milk.sale.details.get.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while fetching milk sale details." + e.getMessage(), e);
        }

    }

    @GetMapping("/milkSaleDetailsFilter")
    public ResponseEntity<BaseResponseDTO<List<MilkSaleDetails>>> getAllMilkSaleDetails(@RequestParam Map<String, String> params) {
        try {
            log.info("Requesting for api/v1/getAllMilkSaleDetails with RequestParam: {}", params);
            List<MilkSaleDetails> milkSaleDetailsFilter = milkSaleService.getAllMilkSaleDetails(params.get("buyerName"),
                    parse(params.get("effectiveDate")), parse(params.get("endDate")));
            BaseResponseDTO<List<MilkSaleDetails>> milkSaleDetailsFilterResponse =
                    new BaseResponseDTO<>(MilkConstant.MILK_SALE_DETAILS_FETCHED_FILTER, milkSaleDetailsFilter);
            return new ResponseEntity<>(milkSaleDetailsFilterResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("milk.sale.details.get.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while fetching milk sale details by filter." + e.getMessage(), e);
        }

    }

    @PostMapping("/calculateBuyerMilkProperty")
    public ResponseEntity<BaseResponseDTO<MilkSaleDetails>> calculateBuyerMilkProperty(@Valid @RequestBody MilkSaleRequestDTO milkSaleRequestDTO) {
        try {
            log.info("Requesting for api/v1/calculateMilkProperty with RequestBody: {}", milkSaleRequestDTO);
            MilkSaleDetails milkSaleDetails = milkSaleService.saveMilkSaleDetails(milkSaleRequestDTO);
            BaseResponseDTO<MilkSaleDetails> milkPropertyResponse =
                    new BaseResponseDTO<>(MilkConstant.MILK_PROPERTIES_CALCULATED, milkSaleDetails);
            return new ResponseEntity<>(milkPropertyResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("milk.sale.details.buyer.calculate.property.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while calculating buyer milk property." + e.getMessage(), e);
        }
    }

    @GetMapping("/milkSaleDetailsPdf")
    public ResponseEntity<InputStreamResource> generatePdf() {
        try {
            ByteArrayInputStream bis = pdfService.generatePdfForMilkSaleDetails();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=default-milk-sale-details.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("shiv.dairy.generate.pdf.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while generating pdf." + e.getMessage(), e);
        }
    }

    @GetMapping("milkSaleDetailsPdfFilter")
    public ResponseEntity<InputStreamResource> getAllMilkSaleDetailsPdf(@RequestParam Map<String, String> param) {
        try {
            LocalDate effectiveDate = LocalDate.parse(param.get("effectiveDate"), DateTimeUtil.dateFormatter);
            LocalDate endDate = LocalDate.parse(param.get("endDate"), DateTimeUtil.dateFormatter);
            ByteArrayInputStream bis = pdfService.generatePdfForMilkSaleDetails(param.get("buyerName"), effectiveDate, endDate);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "inline; filename = "+ param.get("buyerName")+ "_" + DateTimeUtil.date + "_milk-sale-details.pdf");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            log.error(environmentProperties.getProperty("milk.sale.details.generate.pdf.error"), e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while generating pdf." + e.getMessage(), e);
        }
    }
}
