package com.shivdairy.company.controller;

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
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/supplier/{id}")
    public ResponseEntity<SupplierDTO> findById(@PathVariable(required = false) Integer id) {
        log.info("calling findById: /api/v1/supplier/{} ", id);
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @GetMapping("/supplier/search")
    public ResponseEntity<List<SupplierDTO>> search() {
        log.info("calling search: /api/v1/supplier/search");
        return ResponseEntity.ok(supplierService.search());
    }

    @PostMapping("/supplier")
    public ResponseEntity<SupplierDTO> save(@RequestBody @Valid SupplierDTO supplierDTO) {
        log.info("calling save: /api/v1/supplier with RequestBody {}", supplierDTO);
        return ResponseEntity.ok(supplierService.save(supplierDTO));
    }

    @PatchMapping("/supplier/{id}")
    public ResponseEntity<SupplierDTO> partialUpdate(@RequestBody SupplierDTO partialUpdate, @PathVariable Integer id) {
        log.info("calling partialUpdate: /api/v1/supplier/{} with RequestBody {}", id, partialUpdate);
        return ResponseEntity.ok(supplierService.partialUpdate(id, partialUpdate));
    }

    @DeleteMapping("/supplier/{id}")
    public ResponseEntity<SupplierDTO> delete(@PathVariable Integer id){
        log.info("calling delete: /api/v1/supplier/{}", id);
        return ResponseEntity.ok(supplierService.delete(id));
    }

}
