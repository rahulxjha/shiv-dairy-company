package com.shivdairy.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MilkSaleDTO implements MilkProperty {
    @NotNull(message = "Buyer name should not be null.")
    private String name;

    @NotNull(message = "Payment Status should not be null.")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Buyer Milk Weight should not be null.")
    private Double milkWeight;

    @NotNull(message = "Buyer Fat should not be null.")
    private Double fat;

    @NotNull(message = "Buyer CLR should not be null.")
    private Double clr;

    @NotNull(message = "Buyer Milk Rate should not be null.")
    private Double milkRate;
}
