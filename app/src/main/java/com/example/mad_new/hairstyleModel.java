package com.example.mad_new;

public class hairstyleModel {

    String name,price,image,id,gender;

    public hairstyleModel(){

    }

    public hairstyleModel(String name, String price, String image,String id) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id=id;
    }

    public hairstyleModel(String name, String price, String image, String id, String gender) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
