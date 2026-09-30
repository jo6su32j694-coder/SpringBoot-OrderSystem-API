package com.example.ordersystemweb;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // 在 MySQL 建立名為 orders 的資料表
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //讓資料庫自動遞增生成訂單編號(1,2,3...)
    private Long id;

    //多對一關聯(Many-to-One)
    //多筆訂單紀錄可以對應到同一個 Product 商品
    @ManyToOne
    @JoinColumn(name = "product_name",referencedColumnName = "name") //在 orders 表中建立外鑑欄位 product_name
    private Product product;

    private int quantity; //購買數量
    private int totalPrice; //該筆訂單的總金額
    private LocalDateTime orderTime; //訂單成立時間

    //Jpa要求的空建構子
    public Order(){}

    public Order(Product product,int quantity){
        this.product=product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity; //自動計算總價
        this.orderTime = LocalDateTime.now(); //自動記錄當前時間
    }

    //Getter & Setter
    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}

    public Product getProduct(){return product;}
    public void setProduct(Product product){this.product=product;}

    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity){this.quantity=quantity;}

    public int getTotalPrice(){return totalPrice;}
    public void setTotalPrice(int totalPrice){this.totalPrice=totalPrice;}

    public LocalDateTime getOrderTime(){return orderTime;}
    public void setOrderTime(LocalDateTime orderTime){this.orderTime=orderTime;}
}
