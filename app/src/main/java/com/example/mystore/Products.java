package com.example.mystore;

public class Products {

    private String Brand;
    private String Description;



    private String Price;

    private String Rating;
    private String Thumbnail;

    public Products() {
    }

    public Products(String brand, String description, String price, String rating, String thumbnail) {
        Brand = brand;
        Description = description;
        Price = price;
        Rating = rating;
        Thumbnail = thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }

    public String getBrand() {
        return Brand;
    }

    public String getRating() {
        return Rating;
    }
}