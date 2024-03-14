package com.shivdairy.company.repository;

import com.shivdairy.company.dto.SupplierDTO;

import java.util.List;

public interface SupplierRepository {
    SupplierDTO save(SupplierDTO supplier);
    SupplierDTO findById(Integer id);
    List<SupplierDTO> search();
    SupplierDTO findByPhoneNum(String phoneNum);
    SupplierDTO update(SupplierDTO supplierDTO);
    SupplierDTO deleteById(Integer id);
}
