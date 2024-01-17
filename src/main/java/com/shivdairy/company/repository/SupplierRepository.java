package com.shivdairy.company.repository;

import com.shivdairy.company.dto.SupplierDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Slf4j
public class SupplierRepository {
    @Autowired
    private EntityManager entityManager;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime dateTime = LocalDateTime.now();

    public SupplierRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public SupplierDTO save(SupplierDTO supplier) {
        supplier.setCreatedDt(dateTime);
        entityManager.persist(supplier);
        SupplierDTO supplierRes = findById(supplier.getId());
        log.info("supplier {}", supplierRes);
        return supplierRes;
    }

    public SupplierDTO findById(Integer id) {
        return entityManager.find(SupplierDTO.class, id);
    }

    public List<SupplierDTO> search() {
        TypedQuery<SupplierDTO> query =
                entityManager.createQuery("FROM SupplierDTO WHERE isDeleted = FALSE", SupplierDTO.class);
        List<SupplierDTO> supplierList = query.getResultList();
        log.info("supplierList {}", supplierList);
        return supplierList;
    }

    public SupplierDTO findByPhoneNum(String phoneNum) {
        TypedQuery<SupplierDTO> query = entityManager
                .createQuery("FROM SupplierDTO WHERE phoneNum LIKE :phoneNum", SupplierDTO.class);
        query.setParameter("phoneNum", phoneNum);
        SupplierDTO supplierList = query.getSingleResult();
        log.info("supplierList {}", supplierList);
        return supplierList;
    }

    @Transactional
    public SupplierDTO update(SupplierDTO supplierDTO) {
        SupplierDTO supplier = findById(supplierDTO.getId());
        if (!supplier.getIsDeleted()){
            supplier.setCreatedDt(supplier.getCreatedDt());
            supplier.setUpdatedDt(dateTime);
            log.info("supplier:{}", supplierDTO);
            return entityManager.merge(supplierDTO);
        } else {
            log.info("Aborting update as data is deleted");
            return supplier;
        }

    }

    @Transactional
    public SupplierDTO deleteById(Integer id) {
        SupplierDTO supplier = findById(id);
        if (!supplier.getIsDeleted()){
            supplier.setDeletedDt(dateTime);
            supplier.setIsDeleted(Boolean.TRUE);
            log.info("Supplier soft deletion completed");
            return supplier;
        } else {
            log.info("Aborting soft deletion as this data is already deleted");
            return supplier;
        }

    }

}
