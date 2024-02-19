package com.shivdairy.company.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Table(name = "milk_details")
public class MilkDetailsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "milk_sequence")
    @SequenceGenerator(name = "milk_sequence", sequenceName = "milk_sequence", initialValue = 1011, allocationSize = 1)
    private Long id;

    @Column(name = "milk_type", nullable = false)
    private MilkType milkType;

    @Column(name = "milk_amount", nullable = false)
    private Double milkAmount;

    @Column(name = "milk_fat", nullable = false)
    private Double fat;

    @Column(name = "milk_clr", nullable = false)
    private Double clr;

    @Column(name = "milk_snf")
    private Double snf;

    @Column(name = "milk_rate", nullable = false)
    private Double rate;

    @Column(name = "milk_powder")
    private Double powder;

    @Column(name = "supplier_milk_payment")
    private Double milkPayment;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierDTO supplier;

    public Map<String, String> toMap() {
        Map<String, String> milkMap = new HashMap<>();
        if (id != null) milkMap.put("id", id.toString());
        if (milkType != null) milkMap.put("milkType", milkType.toString());
        if (milkAmount != null) milkMap.put("milkAmount", milkAmount.toString());
        if (fat != null) milkMap.put("fat", fat.toString());
        if (snf != null) milkMap.put("snf", snf.toString());
        if (rate != null) milkMap.put("rate", rate.toString());
        if (powder != null) milkMap.put("rate", powder.toString());
        if (milkPayment != null) milkMap.put("milkPayment", milkPayment.toString());
        return milkMap;
    }

}
