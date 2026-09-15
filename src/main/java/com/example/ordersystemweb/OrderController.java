package com.example.ordersystemweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order-system") //點餐系統網址前綴
public class OrderController {

    //模擬網頁後台資料
    private Products milkTea = new Products("奶茶",65,10);
    private Products friedChicken = new Products("炸雞",85,5);
    private Products sausage = new Products("香腸",45,7);

    // 1.監聽 "/api/order-system/products/milk-tea "
    @GetMapping("/products/milk-tea")
    public Products getMilkTea(){
        //直接回傳Java物件，Spring Boot會自動轉成大括號{}的JSON格式
        return milkTea;
    }

    // 2.監聽 "/api/order-system/item-list"
    @GetMapping("/item-list")
    public List<Products> getItemList(){
        List<Products> products = new ArrayList<>();
        products.add(milkTea);
        products.add(friedChicken);
        products.add(sausage);
        return products;
    }
}
