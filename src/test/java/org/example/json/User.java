package org.example.json;

import lombok.*;

@Getter @Setter
public class User {
    int id;
    String name;
    String username;
    String email;
    String phone;
    String website;

    Address address;
    Company company;
}
