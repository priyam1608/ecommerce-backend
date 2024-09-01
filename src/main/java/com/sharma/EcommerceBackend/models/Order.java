package com.sharma.EcommerceBackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String OrderId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;

    private LocalDateTime deliveryDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private Double totalPrice;
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private Integer totalItem;
    private LocalDateTime createdAt;
}
