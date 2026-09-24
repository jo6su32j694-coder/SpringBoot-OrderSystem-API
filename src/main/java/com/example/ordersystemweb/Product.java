package com.example.ordersystemweb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //告訴Spring Boot這是一個資料庫的實體
@Table(name="Products") //對應資料庫中名為"Products"的資料表
public class Product {

    @Id //每個資料表中都要有主鍵(Primary Key)，以商品名為主鍵
    private String name;
    private int price;
    private int stock;

    //JPA要求實體類別必須提供一個「無參數的空建構子」，否則會報錯
    public Product(){};

    //原本的建構子
    public Product(String name, int price, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    //getter & setter
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getPrice() {return price;}
    public void setPrice(int price){this.price = price;}

    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

}
