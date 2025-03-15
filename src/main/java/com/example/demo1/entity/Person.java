package com.example.demo1.entity;

import lombok.*;

@Getter
@Setter
public class Person {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Address address;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Person(int id, String name1, Address ad) {
        this.id = id;
        this.name = name1;
        this.address=ad;
    }
}

