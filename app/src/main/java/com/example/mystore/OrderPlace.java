package com.example.mystore;

import java.util.List;

public class OrderPlace {
    private String address;
    private List<Cart> carts;

    public OrderPlace() {
    }

    public OrderPlace(String address, List<Cart> carts) {
        this.address = address;
        this.carts = carts;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getAddress() {
        return address;
    }

    public List<Cart> getCarts() {
        return carts;
    }
}
