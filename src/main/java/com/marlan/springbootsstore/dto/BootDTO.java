package com.marlan.springbootsstore.dto;

import com.marlan.springbootsstore.enums.BootType;
import lombok.Data;

@Data
public class BootDTO {
    private Integer id;
    private String material;
    private BootType type;
    private Float size;
    private Integer quantity;
}
