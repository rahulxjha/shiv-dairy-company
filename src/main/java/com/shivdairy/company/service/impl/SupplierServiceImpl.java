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
        log.info("inside SupplierServiceImpl.findById({}) ", id);
        SupplierDTO supplierDTO = supplierRepository.findById(id);
        if (supplierDTO == null)
            throw new NoItemFoundException(String.format(supplierNoteFoundException, id));
        else return supplierDTO;
    }

    @Override
    public List<SupplierDTO> search() {
        log.info("inside SupplierServiceImpl.search");
        List<SupplierDTO> supplierDTOS = supplierRepository.search();
        if (supplierDTOS.isEmpty())
            throw new NoItemFoundException(supplierNoteFoundException);
        else return supplierDTOS;
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
        if (!dbSupplier.toMap().isEmpty()){
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
