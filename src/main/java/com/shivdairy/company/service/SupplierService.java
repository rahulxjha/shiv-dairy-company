package com.shivdairy.company.service;

import com.shivdairy.company.dto.SupplierDTO;

public interface SupplierService {
    SupplierDTO findById(Integer id);

    void downloadExcel();

    SupplierDTO partialUpdate(Integer id, SupplierDTO supplierDTO);

    SupplierDTO save(SupplierDTO supplierDTO);

    SupplierDTO delete(Integer id);
}
