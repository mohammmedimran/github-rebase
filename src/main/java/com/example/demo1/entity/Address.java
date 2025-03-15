package com.example.demo1.entity;

import lombok.*;

@Getter
@Setter
public class Address{
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String city;

    public Address(String city) {
        this.city = city;
    }
}

