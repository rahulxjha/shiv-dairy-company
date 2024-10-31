package com.shivdairy.company.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.model.MilkSaleDetails;
import com.shivdairy.company.service.MilkSaleService;
import com.shivdairy.company.service.MilkService;
import com.shivdairy.company.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private MilkSaleService milkSaleService;
    @Autowired
    private MilkService milkService;
    private final Color navyBlue = new Color(70, 130, 180);
    private final Font fieldFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
    private final Font headerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, Color.WHITE);
    private final Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLDITALIC, new Color(70, 130, 180));
    private final Font cellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Color.BLACK);
    private final Color highlightColor = new Color(152, 251, 152);
    private final Font bodyFont = new Font(Font.TIMES_ROMAN, 12);
    private final Font mdHeaderFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, Color.WHITE);
    private final Font mdCellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Color.BLACK);

    @Override
    public ByteArrayInputStream generatePdfForMilkSaleDetails() throws DocumentException {
        List<MilkSaleDetails> saleDetails = milkSaleService.getAllMilkSaleDetails();
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Add title
        Paragraph title = new Paragraph("Shiv Dairy Milk Sale Details", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);


        float[] columnWidths = {2.5f, 3.5f, 2.7f, 2.3f, 2f, 2.5f, 2.5f, 2.5f, 2.3f, 2.5f, 2.2f, 3.5f, 3.5f,
                3.8f, 2.5f, 3.5f, 2.5f, 2.2f, 2.2f};
        PdfPTable table = getTable(19);
        table.setWidths(columnWidths);

        getMilkSaleDetailsTable(saleDetails, table, "");

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private PdfPTable getTable(int columnSize){
        PdfPTable table = new PdfPTable(columnSize);
        table.setWidthPercentage(99); // Table width percentage relative to page width
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);
        return table;
    }

    @Override
    public ByteArrayInputStream generatePdfForMilkDetails() throws DocumentException {
        List<MilkDetails> milkDetailsList = milkService.getAllMilkDetails();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 10, 10, 10, 10); // Small margins
        PdfWriter.getInstance(document, baos);

        document.open();

        Paragraph title = new Paragraph("Shiv Dairy Milk Collection Details", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = getTable(14);

        // Setting column widths, you can adjust the ratio to your needs
        float[] columnWidths = {1.2f, 2.5f, 3, 2, 1.3f, 1.3f, 1.6f, 1.5f, 1.6f, 1.5f, 1.9f, 2, 2, 2.3f};
        table.setWidths(columnWidths);

        getMilkDetailsTable(milkDetailsList, table, "");

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    @Override
    public ByteArrayInputStream generatePdfForMilkDetailsByName(String supplierName, LocalDate effectiveDate,
                                                                LocalDate endDate) throws DocumentException {
        List<MilkDetails> milkDetailsList = milkService.getAllMilkDetailsByName(supplierName, effectiveDate, endDate);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 10, 10, 10, 10); // Small margins
        PdfWriter.getInstance(document, baos);

        document.open();

        Paragraph title = new Paragraph("Shiv Dairy Milk Collection Details", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add Supplier name
        Phrase buyerNamePhrase = new Phrase();
        Chunk nameField = new Chunk("Name: ", fieldFont);
        Chunk nameValue = new Chunk(supplierName, bodyFont);
        buyerNamePhrase.add(nameField);
        buyerNamePhrase.add(nameValue);
        Paragraph nameParagraph = new Paragraph(buyerNamePhrase);
        document.add(nameParagraph);

        double totalWeight = milkDetailsList.stream().mapToDouble(MilkDetails::getMilkWeight).sum();
        Phrase weightPhrase = new Phrase();
        Chunk weightField = new Chunk("Total Weight: ", fieldFont);
        Chunk weightValue = new Chunk(String.valueOf(totalWeight), bodyFont);
        weightPhrase.add(weightField);
        weightPhrase.add(weightValue);
        Paragraph orgWeightParagraph = new Paragraph(weightPhrase);
        orgWeightParagraph.setAlignment(Element.ALIGN_RIGHT);
        orgWeightParagraph.setSpacingBefore(-17f);
        document.add(orgWeightParagraph);

        double totalPayment = milkService.round(milkDetailsList.stream().mapToDouble(MilkDetails::getMilkPayment).sum());
        // Add total payment
        Phrase paymentPhrase = new Phrase();
        Chunk paymentField = new Chunk("Total Payment: ", fieldFont);
        Chunk paymentValue = new Chunk(String.valueOf(totalPayment), bodyFont);
        paymentValue.setBackground(highlightColor);
        paymentPhrase.add(paymentField);
        paymentPhrase.add(paymentValue);
        Paragraph paymentParagraph = new Paragraph(paymentPhrase);
        document.add(paymentParagraph);

        // Add total fat weight
        double totalFatWeight = milkService.round(milkDetailsList.stream().mapToDouble(MilkDetails::getFatWeight).sum());
        Phrase fatWeightPhrase = new Phrase();
        Chunk fatWeightField = new Chunk("Fat Weight: ", fieldFont);
        Chunk fatWeightValue = new Chunk(String.valueOf(totalFatWeight), bodyFont);
        fatWeightPhrase.add(fatWeightField);
        fatWeightPhrase.add(fatWeightValue);
        Paragraph fatWeightParagraph = new Paragraph(fatWeightPhrase);
        fatWeightParagraph.setAlignment(Element.ALIGN_RIGHT);
        fatWeightParagraph.setSpacingBefore(-17f);
        document.add(fatWeightParagraph);

        Phrase periodPhase = new Phrase();
        Chunk periodField = new Chunk("Effective Period: ", fieldFont);
        Chunk periodValue = new Chunk(String.format("%s ~ %s", effectiveDate, endDate), bodyFont);
        periodPhase.add(periodField);
        periodPhase.add(periodValue);
        Paragraph periodParagraph = new Paragraph(periodPhase);
        document.add(periodParagraph);

        // Total SNF weight
        double totalSnfWeight = milkService.round(milkDetailsList.stream().mapToDouble(MilkDetails::getSnfWeight).sum());
        Phrase snfWeightPhrase = new Phrase();
        Chunk snfWeightField = new Chunk("SNF Weight: ", fieldFont);
        Chunk snfWeightValue = new Chunk(String.valueOf(totalSnfWeight), bodyFont);
        snfWeightPhrase.add(snfWeightField);
        snfWeightPhrase.add(snfWeightValue);
        Paragraph snfWeightParagraph = new Paragraph(snfWeightPhrase);
        snfWeightParagraph.setAlignment(Element.ALIGN_RIGHT);
        snfWeightParagraph.setSpacingBefore(-17f);
        document.add(snfWeightParagraph);

        PdfPTable table = getTable(13);
        float[] columnWidths = {1.2f, 2.5f, 1.7f, 1.3f, 1.3f, 1.7f, 1.9f, 1.9f, 1.5f, 1.5f, 1.9f, 2, 2};

        table.setWidths(columnWidths);

        getMilkDetailsTable(milkDetailsList, table, supplierName);

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void addCellToTable(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPaddingTop(4);
        cell.setPaddingBottom(4);
        table.addCell(cell);
    }

    @Override
    public ByteArrayInputStream generatePdfForMilkSaleDetails(String buyerName, LocalDate effectiveDate,
                                                              LocalDate endDate) throws DocumentException {
        List<MilkSaleDetails> milkSaleDetailsList = milkSaleService.getAllMilkSaleDetails(buyerName, effectiveDate, endDate);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 10, 10, 10, 10); // Small margins
        PdfWriter.getInstance(document, baos);

        document.open();

        // Add title
        Paragraph title = new Paragraph("Shiv Dairy Milk Sale Details", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add buyer name
        Phrase buyerNamePhrase = new Phrase();
        Chunk nameField = new Chunk("Name: ", fieldFont);
        Chunk nameValue = new Chunk(buyerName, bodyFont);
        buyerNamePhrase.add(nameField);
        buyerNamePhrase.add(nameValue);
        Paragraph nameParagraph = new Paragraph(buyerNamePhrase);
        document.add(nameParagraph);

        // Original milk weight
        double totalOrgWeight = milkSaleDetailsList.stream().mapToDouble(MilkSaleDetails::getMilkWeight).sum();
        Phrase orgWeightPhrase = new Phrase();
        Chunk orgWeightField = new Chunk("Total Weight(O): ", fieldFont);
        Chunk orgWeightValue = new Chunk(String.valueOf(totalOrgWeight), bodyFont);
        orgWeightPhrase.add(orgWeightField);
        orgWeightPhrase.add(orgWeightValue);
        Paragraph orgWeightParagraph = new Paragraph(orgWeightPhrase);
        orgWeightParagraph.setAlignment(Element.ALIGN_RIGHT);
        orgWeightParagraph.setSpacingBefore(-17f);
        document.add(orgWeightParagraph);

        // Calculate totals
        double totalWeight = milkSaleDetailsList.stream().mapToDouble(MilkSaleDetails::getBuyerMilkWeight).sum();
        double totalPayment = milkService.round(milkSaleDetailsList.stream().mapToDouble(MilkSaleDetails::getMilkPayment).sum());

        // Add total weight
        Phrase weightPhrase = new Phrase();
        Chunk weightField = new Chunk("Total Weight: ", fieldFont);
        Chunk weightValue = new Chunk(String.valueOf(totalWeight), bodyFont);
        weightPhrase.add(weightField);
        weightPhrase.add(weightValue);
        Paragraph weightParagraph = new Paragraph(weightPhrase);
        document.add(weightParagraph);

        // Total SNF weight
        double totalSnfWeight = milkService.round(milkSaleDetailsList.stream().mapToDouble(MilkSaleDetails::getSnfWeight).sum());
        Phrase snfWeightPhrase = new Phrase();
        Chunk snfWeightField = new Chunk("SNF Weight: ", fieldFont);
        Chunk snfWeightValue = new Chunk(String.valueOf(totalSnfWeight), bodyFont);
        snfWeightPhrase.add(snfWeightField);
        snfWeightPhrase.add(snfWeightValue);
        Paragraph snfWeightParagraph = new Paragraph(snfWeightPhrase);
        snfWeightParagraph.setAlignment(Element.ALIGN_RIGHT);
        snfWeightParagraph.setSpacingBefore(-17f);
        document.add(snfWeightParagraph);

        // Add total payment
        Phrase paymentPhrase = new Phrase();
        Chunk paymentField = new Chunk("Total Payment: ", fieldFont);
        Chunk paymentValue = new Chunk(String.valueOf(totalPayment), bodyFont);
        paymentValue.setBackground(highlightColor);
        paymentPhrase.add(paymentField);
        paymentPhrase.add(paymentValue);
        Paragraph paymentParagraph = new Paragraph(paymentPhrase);
        document.add(paymentParagraph);

        // Add total fat weight
        double totalFatWeight = milkService.round(milkSaleDetailsList.stream().mapToDouble(MilkSaleDetails::getFatWeight).sum());
        Phrase fatWeightPhrase = new Phrase();
        Chunk fatWeightField = new Chunk("Fat Weight: ", fieldFont);
        Chunk fatWeightValue = new Chunk(String.valueOf(totalFatWeight), bodyFont);
        fatWeightPhrase.add(fatWeightField);
        fatWeightPhrase.add(fatWeightValue);
        Paragraph fatWeightParagraph = new Paragraph(fatWeightPhrase);
        fatWeightParagraph.setAlignment(Element.ALIGN_RIGHT);
        fatWeightParagraph.setSpacingBefore(-17f);
        document.add(fatWeightParagraph);

        // Add period
        Phrase periodPhase = new Phrase();
        Chunk periodField = new Chunk("Effective Period: ", fieldFont);
        Chunk periodValue = new Chunk(String.format("%s ~ %s", effectiveDate, endDate), bodyFont);
        periodPhase.add(periodField);
        periodPhase.add(periodValue);
        Paragraph periodParagraph = new Paragraph(periodPhase);
        document.add(periodParagraph);

        PdfPTable table = getTable(18);

        float[] columnWidths = {2.9f, 2.3f, 1.7f, 1.3f, 1.8f, 1.9f, 1.9f, 1.9f, 1.9f, 1.5f, 2.4f, 2.4f, 2.7f, 2.9f,
                2.7f, 2.1f, 1.3f, 1.3f};
        table.setWidths(columnWidths);

        getMilkSaleDetailsTable(milkSaleDetailsList, table, buyerName );

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void getMilkDetailsTable(List<MilkDetails> milkDetailsList, PdfPTable table, String supplierName){
        table.addCell(createHeaderCell("Sr.No.", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Date", mdHeaderFont, navyBlue));
        if (supplierName.isBlank()) table.addCell(createHeaderCell("Supplier Name", headerFont, navyBlue));
        table.addCell(createHeaderCell("Weight", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Fat", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("clr", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Fat Weight", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Fat Rate", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("SNF Weight", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("SNF Rate", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Milk Rate", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Fat Amt", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("SNF Amt", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Payment", mdHeaderFont, navyBlue));

        for (MilkDetails milkDetails : milkDetailsList){
            addCellToTable(table, String.valueOf(milkDetails.getId()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getDate()), mdCellFont);
            if (supplierName.isEmpty()) addCellToTable(table, milkDetails.getName(), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getMilkWeight()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getFat()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getClr()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getFatWeight()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getFatRate()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getSnfWeight()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getSnfRate()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getMilkRate()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getFatAmount()), mdCellFont);
            addCellToTable(table, String.valueOf(milkDetails.getSnfAmount()), mdCellFont);
            table.addCell(new PdfPCell(new Phrase(milkDetails.getMilkPayment().toString(), mdCellFont)) {{ setBackgroundColor(highlightColor); }});
        }
    }

    private void getMilkSaleDetailsTable(List<MilkSaleDetails> milkSaleDetails, PdfPTable table, String buyerName) {
        table.addCell(createHeaderCell("Date", headerFont, navyBlue, 90, 4));
        if (buyerName.isBlank()) table.addCell(createHeaderCell("Buyer Name", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Buyer\nMilk Weight", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Buyer Fat", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Buyer clr", headerFont, navyBlue, 90, 4)); // With rotation and padding
        table.addCell(createHeaderCell("Fat Weight", headerFont, navyBlue, 90, 0)); // With rotation and padding
        table.addCell(createHeaderCell("Fat\nRate", headerFont, navyBlue, 90, 0));
        table.addCell(createHeaderCell("SNF\nWeight", headerFont, navyBlue, 90, 0)); // With rotation
        table.addCell(createHeaderCell("SNF %", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("SNF\nRate", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Milk Rate", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Fat\nAmount", headerFont, navyBlue, 90, 0)); // With rotation
        table.addCell(createHeaderCell("SNF\nAmount", headerFont, navyBlue, 90, 0)); // With rotation
        table.addCell(createHeaderCell("Payment", headerFont, navyBlue, 90, 10)); // With rotation and padding
        table.addCell(createHeaderCell("Payment\nStatus", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Seller\nName", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Weight(O)", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Fat(O)", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("clr(O)", headerFont, navyBlue, 90, 4));

        for (MilkSaleDetails saleDetail : milkSaleDetails) {
            table.addCell(new PdfPCell(new Phrase(saleDetail.getDate().toString(), cellFont)) {{ setPadding(2.8f); }});
            if (buyerName.isEmpty()) addCellToTable(table, saleDetail.getBuyerName(), cellFont);
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerMilkWeight().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerFat().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerClr().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFatWeight().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFatRate().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfWeight().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfPercent().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfRate().toString(),cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getMilkRate().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFatAmount().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfAmount().toString(), cellFont)) {{ setPadding(2.8f); }});
            PdfPCell milkPaymentCell = new PdfPCell(new Phrase(saleDetail.getMilkPayment().toString(), cellFont)) {{ setPadding(2.8f); setBackgroundColor(highlightColor); }};
            table.addCell(milkPaymentCell);
            table.addCell(new PdfPCell(new Phrase(saleDetail.getPaymentStatus().name(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSellerName(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getMilkWeight().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFat().toString(), cellFont)) {{ setPadding(2.8f); }});
            table.addCell(new PdfPCell(new Phrase(saleDetail.getClr().toString(), cellFont)) {{ setPadding(2.8f); }});
        }

    }

    // Utility method to create a table header cell
    private PdfPCell createHeaderCell(String text, Font font, Color backgroundColor, int rotation, float padding) {
        PdfPCell headerCell = new PdfPCell(new Phrase(text, font));
        headerCell.setBackgroundColor(backgroundColor);
        if (rotation != 0) {
            headerCell.setRotation(rotation);
        }
        if (padding > 0) {
            headerCell.setPadding(padding);
        }
        return headerCell;
    }

    private PdfPCell createHeaderCell(String text, Font font, Color backgroundColor) {
        return createHeaderCell(text, font, backgroundColor, 0, 0);
    }

}