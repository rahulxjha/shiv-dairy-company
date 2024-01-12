package com.shivdairy.company.service;

import com.shivdairy.company.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    SupplierDTO findById(Integer id);

    List<SupplierDTO> search();

    SupplierDTO partialUpdate(Integer id, SupplierDTO supplierDTO);

    SupplierDTO save(SupplierDTO supplierDTO);
}
