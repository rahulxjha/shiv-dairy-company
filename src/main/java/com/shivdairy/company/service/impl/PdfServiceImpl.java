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
    private final Font cellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 9, Color.BLACK);
    private final Color highlightColor = new Color(152, 251, 152);
    private final Font bodyFont = new Font(Font.TIMES_ROMAN, 12);
    private final Font mdHeaderFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, Color.WHITE);
    private final Font mdCellFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Color.BLACK);

    @Override
    public ByteArrayInputStream generatePdfForMilkSaleDetails() throws DocumentException {
        List<MilkSaleDetails> saleDetails = milkSaleService.getAllMilkSaleDetails();
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        float[] columnWidths = {1f, 2f, 3f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 3f, 2f, 2f, 2f};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

        table.addCell(new PdfPCell(new Phrase("Sr.No.")));
        table.addCell(new PdfPCell(new Phrase("Date")));
        table.addCell(new PdfPCell(new Phrase("Buyer Name")));
        table.addCell(new PdfPCell(new Phrase("Milk Weight (Buyer)")));
        table.addCell(new PdfPCell(new Phrase("Milk Fat (Buyer)")));
        table.addCell(new PdfPCell(new Phrase("Milk CLR (Buyer)")));
        table.addCell(new PdfPCell(new Phrase("Fat Weight")));
        table.addCell(new PdfPCell(new Phrase("Fat Rate")));
        table.addCell(new PdfPCell(new Phrase("SNF Weight")));
        table.addCell(new PdfPCell(new Phrase("SNF Percent")));
        table.addCell(new PdfPCell(new Phrase("SNF Rate")));
        table.addCell(new PdfPCell(new Phrase("Milk Rate")));
        table.addCell(new PdfPCell(new Phrase("Fat Amount")));
        table.addCell(new PdfPCell(new Phrase("SNF Amount")));
        table.addCell(new PdfPCell(new Phrase("Milk Payment")));
        table.addCell(new PdfPCell(new Phrase("Payment Status")));
        table.addCell(new PdfPCell(new Phrase("Seller Name")));
        table.addCell(new PdfPCell(new Phrase("Milk Weight (Seller)")));
        table.addCell(new PdfPCell(new Phrase("Milk Fat (Seller)")));
        table.addCell(new PdfPCell(new Phrase("Milk CLR (Seller)")));

        for (MilkSaleDetails saleDetail : saleDetails) {
            table.addCell(new PdfPCell(new Phrase(saleDetail.getId().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getDate().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerName())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerMilkWeight().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerFat().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getBuyerClr().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFatWeight().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFatRate().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfWeight().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfPercent().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfRate().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getMilkRate().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFatAmount().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSnfAmount().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getMilkPayment().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getPaymentStatus().name())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getSellerName())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getMilkWeight().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getFat().toString())));
            table.addCell(new PdfPCell(new Phrase(saleDetail.getClr().toString())));
        }

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
        List<MilkDetails> milkDetails = milkService.getAllMilkDetails();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 10, 10, 10, 10); // Small margins
        PdfWriter.getInstance(document, baos);

        document.open();

        Paragraph title = new Paragraph("Shiv Dairy Milk Collection Details", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = getTable(15);

        // Setting column widths, you can adjust the ratio to your needs
        float[] columnWidths = {1.2f, 2.5f, 3, 2, 1.3f, 1.3f, 1.6f, 1.5f, 1.6f, 1.5f, 1.9f, 1, 2, 2, 2.3f};
        table.setWidths(columnWidths);

        table.addCell(createHeaderCell("Sr.No.", headerFont, navyBlue));
        table.addCell(createHeaderCell("Date", headerFont, navyBlue));
        table.addCell(createHeaderCell("Supplier Name", headerFont, navyBlue));
        table.addCell(createHeaderCell("Milk Weight", headerFont, navyBlue));
        table.addCell(createHeaderCell("Fat", headerFont, navyBlue));
        table.addCell(createHeaderCell("CLR", headerFont, navyBlue));
        table.addCell(createHeaderCell("Fat Kg", headerFont, navyBlue));
        table.addCell(createHeaderCell("Fat Rate", headerFont, navyBlue));
        table.addCell(createHeaderCell("SNF Kg", headerFont, navyBlue));
        table.addCell(createHeaderCell("SNF %", headerFont, navyBlue));
        table.addCell(createHeaderCell("SNF Rate", headerFont, navyBlue));
        table.addCell(createHeaderCell("Milk Rate", headerFont, navyBlue));
        table.addCell(createHeaderCell("Fat Pay", headerFont, navyBlue));
        table.addCell(createHeaderCell("SNF Pay", headerFont, navyBlue));
        table.addCell(createHeaderCell("Milk Payment ", headerFont, navyBlue));

        // Adding data rows with a smaller font
        for (MilkDetails milkDetail : milkDetails) {
            table.addCell(new PdfPCell(new Phrase(milkDetail.getId().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getDate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getMilkWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getFat().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getClr().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getFatWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getFatRate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getSnfWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getSnfPercent().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getSnfRate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getMilkRate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getFatAmount().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkDetail.getSnfAmount().toString(), cellFont)));
            PdfPCell milkPaymentCell = new PdfPCell(new Phrase(milkDetail.getMilkPayment().toString(), cellFont));
            milkPaymentCell.setBackgroundColor(highlightColor);
            table.addCell(milkPaymentCell);
        }

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

        table.addCell(createHeaderCell("Sr.No.", mdHeaderFont, navyBlue));
        table.addCell(createHeaderCell("Date", mdHeaderFont, navyBlue));
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
            PdfPCell milkPaymentCell = new PdfPCell(new Phrase(milkDetails.getMilkPayment().toString(), mdCellFont));
            milkPaymentCell.setBackgroundColor(highlightColor);
            table.addCell(milkPaymentCell);
        }

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

        float[] columnWidths = {1.2f, 2.5f, 2.2f, 1.3f, 1.3f, 1.9f, 1.9f, 1.9f, 1.9f, 1.5f, 2.4f, 2.4f, 2.7f, 2.9f,
                2.7f, 2.1f, 1.3f, 1.3f};
        table.setWidths(columnWidths);

        table.addCell(createHeaderCell("Sr.No.", headerFont, navyBlue));
        table.addCell(createHeaderCell("Payment Status", headerFont, navyBlue));
        table.addCell(createHeaderCell("Buyer Milk Weight", headerFont, navyBlue));
        table.addCell(createHeaderCell("Buyer Fat", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Buyer clr", headerFont, navyBlue, 90, 4)); // With rotation and padding
        table.addCell(createHeaderCell("Fat Weight", headerFont, navyBlue, 90, 4)); // With rotation and padding
        table.addCell(createHeaderCell("Fat Rate", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("SNF Weight", headerFont, navyBlue, 90, 0)); // With rotation
        table.addCell(createHeaderCell("SNF Rate", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Milk Rate", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Fat Amount", headerFont, navyBlue, 90, 0)); // With rotation
        table.addCell(createHeaderCell("SNF Amount", headerFont, navyBlue, 90, 0)); // With rotation
        table.addCell(createHeaderCell("Payment", headerFont, navyBlue, 90, 10)); // With rotation and padding
        table.addCell(createHeaderCell("Date", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Seller Name", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Weight(O)", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("Fat(O)", headerFont, navyBlue, 90, 4));
        table.addCell(createHeaderCell("clr(O)", headerFont, navyBlue, 90, 4));

        for (MilkSaleDetails milkSaleDetails : milkSaleDetailsList){
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getId().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getPaymentStatus().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getBuyerMilkWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getBuyerFat().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getBuyerClr().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getFatWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getFatRate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getSnfWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getSnfRate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getMilkRate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getFatAmount().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getSnfAmount().toString(), cellFont)));
            PdfPCell milkPaymentCell = new PdfPCell(new Phrase(milkSaleDetails.getMilkPayment().toString(), cellFont));
            milkPaymentCell.setBackgroundColor(highlightColor);
            table.addCell(milkPaymentCell);
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getDate().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getSellerName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getMilkWeight().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getFat().toString(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(milkSaleDetails.getClr().toString(), cellFont)));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(baos.toByteArray());
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