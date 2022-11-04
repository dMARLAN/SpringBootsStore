package com.marlan.springbootsstore.dto;

import com.marlan.springbootsstore.enums.BootType;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BootDTO {
    private Integer id;

    private String material;

    private BootType type;

    @Min(value = 0, message = "Size must be greater than 0")
    private Float size;

    @Min(value = 0, message = "Quantity must be greater than 0")
    private Integer quantity;
}
