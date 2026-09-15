package com.example.ordersystemweb;

public class Products {
    private String name;
    private int price;
    private int stock;

    public Products(String name, int price, int stock){
        this.name=name;
        this.price=price;
        this.stock=stock;
    }

    public String getName() {return name;}
    public int getPrice() {return price;}
    public int getStock() {return stock;}

    public void setStock(int stock) {this.stock = stock;}

}
