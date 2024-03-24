package com.shivdairy.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MilkDetailsRequestDTO {
    private String name;
    @NotNull(message = "Milk Weight should not be null.")
    private Double milkWeight;
    @NotNull(message = "Fat should not be null.")
    private Double fat;
    @NotNull(message = "CLR should not be null.")
    private Double clr;
    @NotNull(message = "Milk Rate should not be null.")
    private Double milkRate;
}
