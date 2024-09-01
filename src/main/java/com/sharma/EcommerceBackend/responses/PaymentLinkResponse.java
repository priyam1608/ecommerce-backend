package com.sharma.EcommerceBackend.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLinkResponse {

    private String payment_link_id;
    private String payment_link_url;
}
