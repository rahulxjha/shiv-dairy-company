package com.shivdairy.company.service;

import com.lowagie.text.DocumentException;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

public interface PdfService {
    ByteArrayInputStream generatePdfForMilkSaleDetails() throws DocumentException;
    ByteArrayInputStream generatePdfForMilkDetailsByName(String supplierName, LocalDate effectiveDate, LocalDate endDate) throws DocumentException;
    ByteArrayInputStream generatePdfForMilkDetails() throws DocumentException;
    ByteArrayInputStream generatePdfForMilkSaleDetails(String buyerName, LocalDate effectiveDate, LocalDate endDate) throws DocumentException;
}
