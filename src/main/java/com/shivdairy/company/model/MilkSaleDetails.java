package com.shivdairy.company.model;

import com.shivdairy.company.dto.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "milk_sale_details")
public class MilkSaleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "milk_payment")
    private Double milkPayment;

    @Column(name = "buyer_milk_weight", nullable = false)
    private Double buyerMilkWeight;

    @Column(name = "buyer_milk_fat", nullable = false)
    private Double buyerFat;

    @Column(name = "fat_weight")
    private Double fatWeight;

    @Column(name = "fat_rate")
    private Double fatRate;

    @Column(name = "fat_amount")
    private Double fatAmount;

    @Column(name = "snf_weight")
    private Double snfWeight;

    @Column(name = "snf_percent")
    private Double snfPercent;

    @Column(name = "snf_rate")
    private Double snfRate;

    @Column(name = "snf_amount")
    private Double snfAmount;

    @Column(name = "buyer_milk_clr", nullable = false)
    private Double buyerClr;

    @Column(name = "milk_rate", nullable = false)
    private Double milkRate;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_milk_weight")
    private Double milkWeight;

    @Column(name = "seller_fat")
    private Double fat;

    @Column(name = "seller_clr")
    private Double clr;
}
