package com.shivdairy.company.model;

import com.shivdairy.company.dto.MilkType;
import com.shivdairy.company.dto.SupplierDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name = "milk_details")
public class MilkDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "milk_sequence")
    @SequenceGenerator(name = "milk_sequence", sequenceName = "milk_sequence", initialValue = 1011, allocationSize = 1)
    private Long id;

    @Column(name = "milk_type", nullable = false)
    private MilkType milkType;

    @Column(name = "name")
    private String name;

    @Column(name = "milk_weight", nullable = false)
    private Double milkWeight;

    @Column(name = "milk_fat", nullable = false)
    private Double fat;

    @Column(name = "fat_weight")
    private Double fatWeight;

    @Column(name = "fat_rate")
    private Double fatRate;

    @Column(name = "fat_amount")
    private Double fatAmount;

    @Column(name = "milk_clr", nullable = false)
    private Double clr;

    @Column(name = "snf_weight")
    private Double snfWeight;

    @Column(name = "snf_percent")
    private Double snfPercent;

    @Column(name = "snf_rate")
    private Double snfRate;

    @Column(name = "snf_amount")
    private Double snfAmount;

    @Column(name = "milk_rate", nullable = false)
    private Double milkRate;

    @Column(name = "supplier_milk_payment")
    private Double milkPayment;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierDTO supplier;

    public Map<String, String> toMap() {
        Map<String, String> milkMap = new HashMap<>();
        if (id != null) milkMap.put("id", id.toString());
        if (milkType != null) milkMap.put("milkType", milkType.toString());
        if (milkWeight != null) milkMap.put("milkAmount", milkWeight.toString());
        if (fat != null) milkMap.put("fat", fat.toString());
        if (fatWeight != null) milkMap.put("fatWeight", fatWeight.toString());
        if (fatAmount != null) milkMap.put("fatAmount", fatAmount.toString());
        if (fatRate != null) milkMap.put("fatRate", fatRate.toString());
        if (clr != null) milkMap.put("clr", clr.toString());
        if (snfWeight != null) milkMap.put("snf", snfWeight.toString());
        if (snfAmount != null) milkMap.put("snfAmount", snfAmount.toString());
        if (snfPercent != null) milkMap.put("snfPercent", snfPercent.toString());
        if (snfRate != null) milkMap.put("snfRate", snfRate.toString());
        if (milkRate != null) milkMap.put("milkRate", milkRate.toString());
        if (milkPayment != null) milkMap.put("milkPayment", milkPayment.toString());
        if (date != null) milkMap.put("date", date.toString());
        return milkMap;
    }

}
