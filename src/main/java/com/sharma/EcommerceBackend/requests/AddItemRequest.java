package com.sharma.EcommerceBackend.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequest {

    private Long productId;
    private String size;
    private int quantity;
    private Integer price;
}
