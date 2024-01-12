package com.shivdairy.company.service.impl;

import com.shivdairy.company.dto.SupplierDTO;
import com.shivdairy.company.exception.NoItemFoundException;
import com.shivdairy.company.repository.SupplierRepository;
import com.shivdairy.company.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        log.info("Call QuoteServiceImpl.findById({}) ", id);
        SupplierDTO supplierDTO = supplierRepository.findById(id);
        if (supplierDTO == null)
            throw new NoItemFoundException(String.format(supplierNoteFoundException, id));
        else return supplierDTO;
    }

    @Override
    public List<SupplierDTO> search() {

        return null;
    }

    @Override
    public SupplierDTO partialUpdate(Integer id, SupplierDTO supplierDTO) {
        return null;
    }

    @Override
    public SupplierDTO save(SupplierDTO supplierDTO) {
        return null;
    }
}
