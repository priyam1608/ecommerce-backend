package com.sharma.EcommerceBackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {

    @Column(name = "cardholder_name")
    private String cardHolderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name="cvv")
    private String cvv;
}
