package com.shivdairy.company.service.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.service.MilkSaleService;
import com.shivdairy.company.service.MilkService;
import com.shivdairy.company.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private MilkSaleService milkSaleService;
    @Autowired
    private MilkService milkService;

    @Override
    public ByteArrayInputStream generatePdfForMilkSaleDetails() {
        List<MilkSaleDetails> saleDetails = milkSaleService.getAllMilkSaleDetails();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Shiv Dairy Milk Sale Details"));

        int columnSize = 20;
        Table table = new Table(columnSize);
        table.setFontSize(7.2F);
        table.setMarginLeft(-20);
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Buyer Name")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Weight (Buyer)")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Fat (Buyer)")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk CLR (Buyer)")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat Weight")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat Rate")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Weight")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Percent")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Rate")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Rate")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Payment")));
        table.addHeaderCell(new Cell().add(new Paragraph("Payment Status")));
        table.addHeaderCell(new Cell().add(new Paragraph("Seller Name")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Weight (Seller)")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Fat (Seller)")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk CLR (Seller)")));

        for (MilkSaleDetails saleDetail : saleDetails) {
            table.addCell(new Cell().add(new Paragraph(saleDetail.getId().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getBuyerName())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getBuyerMilkWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getBuyerFat().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getBuyerClr().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getDate().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getFatAmount().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getFatWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getFatRate().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getSnfWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getSnfAmount().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getSnfPercent().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getSnfRate().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getMilkRate().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getMilkPayment().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getPaymentStatus().name())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getSellerName())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getMilkWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getFat().toString())));
            table.addCell(new Cell().add(new Paragraph(saleDetail.getClr().toString())));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    public ByteArrayInputStream generatePdfForMilkDetails() {
        List<MilkDetails> milkDetails = milkService.getAllMilkDetails();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Shiv Dairy Milk Collection Details"));

        int columnSize = 16;
        Table table = new Table(columnSize);
        table.setFontSize(8);
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Supplier Name")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Weight")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat")));
        table.addHeaderCell(new Cell().add(new Paragraph("CLR")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat Weight")));
        table.addHeaderCell(new Cell().add(new Paragraph("Fat Rate")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Weight")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Percent")));
        table.addHeaderCell(new Cell().add(new Paragraph("SNF Rate")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Rate")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Payment")));
        table.addHeaderCell(new Cell().add(new Paragraph("Milk Type")));

        for (MilkDetails milkDetail : milkDetails) {
            table.addCell(new Cell().add(new Paragraph(milkDetail.getId().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getName())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getMilkWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getFat().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getClr().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getDate().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getFatAmount().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getFatWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getFatRate().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getSnfWeight().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getSnfAmount().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getSnfPercent().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getSnfRate().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getMilkRate().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getMilkPayment().toString())));
            table.addCell(new Cell().add(new Paragraph(milkDetail.getMilkType().toString())));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }
}

