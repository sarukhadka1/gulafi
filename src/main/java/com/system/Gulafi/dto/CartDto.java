package com.system.Gulafi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Integer id;
    private Integer productId;
    private Integer userId;
    private Integer quantity;
    private Integer totalPrice;
    private String status;
    private String size;
}
