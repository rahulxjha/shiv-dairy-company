package com.shivdairy.company.repository;

import com.shivdairy.company.dto.SupplierDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
@Slf4j
public class SupplierRepository {
    @Autowired
    private EntityManager entityManager;

    public SupplierRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public SupplierDTO save(SupplierDTO supplier){
        SupplierDTO supplierRes = update(supplier);
        log.info("supplier {}", supplierRes);
        return supplierRes;
    }

    public SupplierDTO findById(Integer id){
        return entityManager.find(SupplierDTO.class, id);
    }

    public List<SupplierDTO> findAll(){
        TypedQuery<SupplierDTO> query = entityManager.createQuery("FROM Supplier", SupplierDTO.class);
        List<SupplierDTO> supplierList = query.getResultList();
        log.info("supplierList {}", supplierList);
        return supplierList;
    }

    public List<SupplierDTO> findByName(String name){
        TypedQuery<SupplierDTO> query = entityManager.createQuery("FROM Supplier WHERE name LIKE :name", SupplierDTO.class);
        query.setParameter("name", name);
        List<SupplierDTO> supplierList = query.getResultList();
        log.info("supplierList {}", supplierList);
        return supplierList;
    }

    @Transactional
    public SupplierDTO update(SupplierDTO supplier){
        supplier.setUpdatedDt(Date.valueOf(LocalDate.now()));
        log.info("supplier:{}", supplier);
        return entityManager.merge(supplier);
    }

    @Transactional
    public void deleteById(Integer id) {
        SupplierDTO supplier = findById(id);
        supplier.setIsDeleted(Boolean.TRUE);
        log.info("Supplier soft deletion completed");
    }

}
