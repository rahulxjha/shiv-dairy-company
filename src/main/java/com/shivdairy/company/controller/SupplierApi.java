package com.shivdairy.company.controller;

import com.shivdairy.company.dto.BaseResponseDTO;
import com.shivdairy.company.dto.SupplierDTO;
import com.shivdairy.company.service.SupplierService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "public/api/v1")
public class SupplierApi {
    private static final String SUPPLIER_FOUND = "Supplier found successfully";
    private static final String SUPPLIER_SEARCHED = "Supplier searched successfully";
    private static final String SUPPLIER_CREATED = "Supplier created successfully";
    private static final String SUPPLIER_UPDATED = "Supplier updated successfully";
    private static final String SUPPLIER_DELETED = "Supplier deleted successfully";
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/{id}")
    public ResponseEntity<BaseResponseDTO<SupplierDTO>> findById(@PathVariable(required = false) Integer id) {
        log.info("calling findById: /api/v1/supplier/{} ", id);
        SupplierDTO supplier = supplierService.findById(id);
        BaseResponseDTO<SupplierDTO> baseResponseDTO = new BaseResponseDTO<>(SUPPLIER_FOUND, supplier);
        return ResponseEntity.ok(baseResponseDTO);
    }

    @GetMapping("/supplier/search")
    public ResponseEntity<BaseResponseDTO<List<SupplierDTO>>> search() {
        log.info("calling search: /api/v1/supplier/search");
        List<SupplierDTO> suppliers = supplierService.search();
        log.info("Supplier List {}", suppliers);
        BaseResponseDTO<List<SupplierDTO>> baseResponseDTO = new BaseResponseDTO<>(SUPPLIER_SEARCHED, suppliers);
        return ResponseEntity.ok(baseResponseDTO);
    }

    @PostMapping("/supplier")
    public ResponseEntity<BaseResponseDTO<SupplierDTO>> save(@Valid @RequestBody SupplierDTO supplierDTO) {
        log.info("calling save: /api/v1/supplier with RequestBody {}", supplierDTO);
        SupplierDTO supplier = supplierService.save(supplierDTO);
        log.info("Saved the supplier {}", supplier);
        BaseResponseDTO<SupplierDTO> baseResponseDTO = new BaseResponseDTO<>(SUPPLIER_CREATED, supplier);
        return ResponseEntity.ok(baseResponseDTO);
    }

    // FIXME: Function is not working properly for its desired requirement, need to fix it.
    @PutMapping("/supplier/{id}")
    public ResponseEntity<BaseResponseDTO<SupplierDTO>> partialUpdate(@Valid @RequestBody SupplierDTO partialUpdate, @PathVariable Integer id) {
        log.info("calling partialUpdate: /api/v1/supplier/{} with RequestBody {}", id, partialUpdate);
        SupplierDTO supplier = supplierService.partialUpdate(id, partialUpdate);
        log.info("Updated the supplier {}", supplier);
        BaseResponseDTO<SupplierDTO> baseResponseDTO = new BaseResponseDTO<>(SUPPLIER_UPDATED, supplier);
        return ResponseEntity.ok(baseResponseDTO);
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<BaseResponseDTO<SupplierDTO>> delete(@PathVariable Integer id){
        log.info("calling delete: /api/v1/supplier/{}", id);
        SupplierDTO supplier = supplierService.delete(id);
        log.info("Deleted the supplier {}", supplier);
        BaseResponseDTO<SupplierDTO> baseResponseDTO = new BaseResponseDTO<>(SUPPLIER_DELETED, supplier);
        return ResponseEntity.ok(baseResponseDTO);
    }

}
