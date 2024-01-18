package com.shivdairy.company.repository;

import com.shivdairy.company.dto.SupplierDTO;
import com.shivdairy.company.exception.NoItemFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    @Value("${supplier.notFound.exception.message}")
    private String supplierNotFoundException;

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
        if (supplier != null && !supplier.getIsDeleted()) {
            supplier.setUpdatedDt(dateTime);
            log.info("Updating supplier: {}", supplierDTO);
            return entityManager.merge(supplier);
        }else throw new NoItemFoundException(String.format(supplierNotFoundException, supplierDTO.getId()));
    }

    @Transactional
    public SupplierDTO deleteById(Integer id) {
        SupplierDTO supplier = findById(id);
        if (supplier != null && !supplier.getIsDeleted()) {
            supplier.setDeletedDt(dateTime);
            supplier.setIsDeleted(true);
            log.info("Soft deletion completed for supplier with ID: {}", id);
            return entityManager.merge(supplier);
        } else throw new NoItemFoundException(String.format(supplierNotFoundException, id));
    }

}
