package org.example.json;

import lombok.*;

@Getter @Setter
public class Address {
    String street;
    String suit;
    String city;
    String zipcode;

    Geo geo;
}
