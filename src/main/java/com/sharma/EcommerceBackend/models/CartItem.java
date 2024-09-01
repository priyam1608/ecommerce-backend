package com.sharma.EcommerceBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Product product;

    private String size;
    private Integer quantity;
    private Integer price;
    private Integer discountedPrice;
    private Long userId;


}
