package com.shivdairy.company.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "supplier_details")
public class SupplierDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;
    @Column(name = "supplier_name", nullable = false, length = 50)
    private String name;
    @Column(name = "phone_num", nullable = false, length = 10)
    private String phoneNum;
    @Column(name = "supplier_email", length = 50, unique = true)
    private String email;
    @Column(name = "address", nullable = false, length = 150)
    private String address;
    @CreatedDate
    @Column(name = "created_dt", nullable = false)
    private Date createdDt;
    @Column(name = "updated_dt")
    private Date updatedDt;
    @Column(name = "supplier_payment_status")
    private PaymentStatus paymentStatus;
    @Column
    private Boolean isDeleted;

    public Map<String, String> toMap() {
        Map<String, String> supplierMap = new HashMap<>();
        if (id != null) supplierMap.put("id", id.toString());
        if (name != null) supplierMap.put("name", name);
        if (phoneNum != null) supplierMap.put("phoneNum", phoneNum);
        if (email != null) supplierMap.put("email", email);
        if (address != null) supplierMap.put("address", address);
        if (createdDt != null) supplierMap.put("createdDt", createdDt.toString());
        if (updatedDt != null) supplierMap.put("updatedDt", updatedDt.toString());
        if (paymentStatus != null) supplierMap.put("paymentStatus", paymentStatus.toString());
        return supplierMap;
    }

}
