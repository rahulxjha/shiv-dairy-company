package com.shivdairy.company.model;

import com.shivdairy.company.dto.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MilkPaymentSummary {
    private List<MilkPaymentDetails> milkPaymentDetails;
    private Double totalMilkPayment;
    public MilkPaymentSummary(List<MilkPaymentDetails> milkPaymentDetails) {
        this.milkPaymentDetails = milkPaymentDetails;
        this.totalMilkPayment = milkPaymentDetails.stream().mapToDouble(MilkPaymentDetails::getMilkPayment).sum();

    }

    @Getter
    @AllArgsConstructor
    public static class MilkPaymentDetails {
        private LocalDate date;
        private Double milkPayment;
        private PaymentStatus paymentStatus;
    }
}
