package com.sn77.crio.criofashion.date20200621954;

public class Products {

    public Products(){

    }

    public Products(String name, String price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    String name;
   String price;
   String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }






}


