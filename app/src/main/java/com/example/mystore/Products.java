package com.example.mystore;

public class Products {
    private String Product;
private String DetailID;
    private String Brand;
    private String Description;



    private String Price;

    private String Rating;
    private String Thumbnail;

    public Products() {
    }

    public Products(String product, String detailID, String brand, String description, String price, String rating, String thumbnail) {
        Product = product;
        DetailID = detailID;
        Brand = brand;
        Description = description;
        Price = price;
        Rating = rating;
        Thumbnail = thumbnail;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getProduct() {
        return Product;
    }

    public void setDetailID(String detailID) {
        DetailID = detailID;
    }

    public String getDetailID() {
        return DetailID;
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