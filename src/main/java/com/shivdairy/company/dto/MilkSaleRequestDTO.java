package com.shivdairy.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MilkSaleRequestDTO {
    @NotNull(message = "MilkSale should not be null.")
    private MilkSale milkSale;

    @NotNull(message = "MilkDetailsRequestDTO should not be null.")
    private MilkDetailsRequestDTO milkDetailsRequestDTO;
}
