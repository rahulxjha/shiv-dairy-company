package com.shivdairy.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MilkSaleRequestDTO {
    @NotNull(message = "MilkSaleDTO should not be null.")
    private MilkSaleDTO buyerMilkDetails;

    @NotNull(message = "MilkDetailsRequestDTO should not be null.")
    private MilkDetailsRequestDTO sellerMilkDetails;
}
