package com.shivdairy.company.dto;

import com.shivdairy.company.model.MilkDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "supplier_details")
public class SupplierDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(name = "supplier_name")
    @NotBlank(message = "Invalid or missing Supplier Name")
    @Length(max = 50)
    private String name;

    @Column(name = "phone_num", nullable = false, length = 10)
    @NotNull(message = "Invalid or missing Phone Number")
    @Pattern(regexp = "^(\\+91|0)?[6789]\\d{9}$", message = "Invalid or missing Phone Number")
    private String phoneNum;

    @Column(name = "supplier_email", unique = true)
    @Length(max = 256)
    @Email(message = "Invalid or missing Contact Email.", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @NotBlank(message = "Invalid or missing Contact Email.")
    private String email;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "Invalid or missing Supplier Address")
    @Length(max = 150)
    private String address;

    @CreatedDate
    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    private LocalDateTime updatedDt;

    @Column(name = "supplier_payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.NOT_DONE;

    @Column
    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "deleted_dt")
    private LocalDateTime deletedDt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    private List<MilkDetails> milkDetails;

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

    public SupplierDTO toEntity() {
        SupplierDTO entity = new SupplierDTO();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setPhoneNum(this.phoneNum);
        entity.setEmail(this.email);
        entity.setAddress(this.address);
        entity.setCreatedDt(this.getCreatedDt());
        entity.setUpdatedDt(this.updatedDt);
        entity.setPaymentStatus(this.paymentStatus);
        entity.setDeletedDt(this.deletedDt);
        return entity;
    }
}
