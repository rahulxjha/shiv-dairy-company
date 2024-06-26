package com.shivdairy.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MilkSale {
    @NotNull(message = "Buyer name should not be null.")
    private String buyer;

    @NotNull(message = "Payment Status should not be null.")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Date should not be null.")
    private LocalDate date;

    @NotNull(message = "Buyer Milk Weight should not be null.")
    private Double milkWeight;

    @NotNull(message = "Buyer Fat should not be null.")
    private Double fat;

    @NotNull(message = "Buyer CLR should not be null.")
    private Double clr;
}
