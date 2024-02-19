package com.shivdairy.company.service.impl;

import com.shivdairy.company.dto.MilkDetailsDTO;
import com.shivdairy.company.dto.SupplierDTO;
import com.shivdairy.company.exception.NoItemFoundException;
import com.shivdairy.company.repository.SupplierRepository;
import com.shivdairy.company.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Value("${supplier.notFound.exception.message}")
    private String supplierNoteFoundException;

    @Override
    public SupplierDTO findById(Integer id) {
        log.info("inside SupplierServiceImpl.findById({}) ", id);
        SupplierDTO supplierDTO = supplierRepository.findById(id);
        if (supplierDTO == null)
            throw new NoItemFoundException(String.format(supplierNoteFoundException, id));
        else return supplierDTO;
    }

    @Override
    public void downloadExcel() {
        log.info("inside SupplierServiceImpl.downloadExcel");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Column 1");
            headerRow.createCell(1).setCellValue("Column 2");
            // Fetch data from your method
            List<SupplierDTO> supplierDTOS = supplierRepository.search();
            // Populate data rows
            int rowNum = 1;
            for (SupplierDTO data : supplierDTOS) {
                List<MilkDetailsDTO> milkDetailsList = data.getMilkDetailsDTO();
                // If the supplier has associated MilkDetailsDTO, iterate over them
                if (!milkDetailsList.isEmpty()) {
                    for (MilkDetailsDTO milkDetails : milkDetailsList) {
                        Row dataRow = sheet.createRow(rowNum++);
                        // Populate SupplierDTO data
                        dataRow.createCell(0).setCellValue(data.getId());
                        dataRow.createCell(1).setCellValue(data.getName());
                        dataRow.createCell(2).setCellValue(data.getPhoneNum());
                        dataRow.createCell(3).setCellValue(data.getEmail());
                        dataRow.createCell(4).setCellValue(data.getAddress());
                        dataRow.createCell(5).setCellValue(data.getPaymentStatus().ordinal());
                        dataRow.createCell(6).setCellValue(data.getCreatedDt());
                        dataRow.createCell(7).setCellValue(data.getUpdatedDt());
                        dataRow.createCell(8).setCellValue(data.getDeletedDt());
                        dataRow.createCell(9).setCellValue(data.getIsDeleted());

                        // Populate MilkDetailsDTO data
                        dataRow.createCell(10).setCellValue(milkDetails.getId());
                        dataRow.createCell(11).setCellValue(milkDetails.getClr());
                        dataRow.createCell(12).setCellValue(milkDetails.getFat());
                        dataRow.createCell(13).setCellValue(milkDetails.getSnf());
                        dataRow.createCell(14).setCellValue(milkDetails.getPowder());
                        dataRow.createCell(15).setCellValue(milkDetails.getMilkType().ordinal());
                        dataRow.createCell(16).setCellValue(milkDetails.getMilkAmount());
                        dataRow.createCell(17).setCellValue(milkDetails.getRate());
                        dataRow.createCell(18).setCellValue(milkDetails.getMilkPayment());
                    }
                } else {
                    // If the supplier has no associated MilkDetailsDTO, create a single row for the supplier
                    Row dataRow = sheet.createRow(rowNum++);
                    dataRow.createCell(0).setCellValue(data.getId());
                    dataRow.createCell(1).setCellValue(data.getName());
                    dataRow.createCell(2).setCellValue(data.getPhoneNum());
                    dataRow.createCell(3).setCellValue(data.getEmail());
                    dataRow.createCell(4).setCellValue(data.getAddress());
                    dataRow.createCell(5).setCellValue(data.getPaymentStatus().ordinal());
                    dataRow.createCell(6).setCellValue(data.getCreatedDt());
                    dataRow.createCell(7).setCellValue(data.getUpdatedDt());
                    dataRow.createCell(8).setCellValue(data.getDeletedDt());
                    dataRow.createCell(9).setCellValue(data.getIsDeleted());
                }
            }
            // Save the workbook to a file
            try (FileOutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file has been generated successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SupplierDTO partialUpdate(Integer id, SupplierDTO supplierDTO) {
        log.info("inside SupplierServiceImpl.partialUpdate({}, {})", id, supplierDTO);
        return supplierRepository.update(supplierDTO);
    }

    @Override
    public SupplierDTO save(SupplierDTO supplierDTO) {
        log.info("inside SupplierServiceImpl.save{} ", supplierDTO);
        SupplierDTO dbSupplier = supplierRepository.findByPhoneNum(supplierDTO.getPhoneNum());
        if (dbSupplier != null){
            log.info("Found existing supplier with id: {}, name: '{}', created_dt: {}",
                    dbSupplier.getId(), dbSupplier.getName(), dbSupplier.getCreatedDt());
            return dbSupplier;
        }
        else {
            log.info("Creating new supplier for '{}', {} ", supplierDTO.getName(), supplierDTO.getPhoneNum());
            return supplierRepository.save(supplierDTO);
        }
    }

    @Override
    public SupplierDTO delete(Integer id) {
        return supplierRepository.deleteById(id);
    }


}
