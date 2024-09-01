package com.sharma.EcommerceBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String StreetAddress;

    private String city;

    private String state;

    private String zipcode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String mobile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(StreetAddress, address.StreetAddress) &&
                Objects.equals(city, address.city) &&
                Objects.equals(state, address.state) &&
                Objects.equals(zipcode, address.zipcode) &&
                Objects.equals(mobile, address.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(StreetAddress, city, state, zipcode, mobile);
    }
}
