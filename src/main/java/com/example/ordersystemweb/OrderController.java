package com.example.ordersystemweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    //新增：處理點單購買的POST API
    //網址將會是"/api/order-system/buy?itemname=奶茶"
    @PostMapping("/buy")
    public String buyItem(@RequestParam String itemName){

        //1.根據前端傳過來的商品名稱，尋找對應的商品物件
        Products targetProduct = null;
        if(milkTea.getName().equals(itemName)){
            targetProduct = milkTea;
        }else if(friedChicken.getName().equals(itemName)){
            targetProduct = friedChicken;
        }else if(sausage.getName().equals(itemName)){
            targetProduct = sausage;
        }

        //2.檢查有沒有找到商品
        if (targetProduct == null){
            return "錯誤：找不到名為「"+itemName+"」的商品！";
        }

        //3.檢查庫存並扣除
        if (targetProduct.getStock()>0){
            targetProduct.setStock(targetProduct.getStock()-1);//扣除1個庫存
            return "購買成功！「"+targetProduct.getName()+"」已加入購物車，剩餘庫存： "+targetProduct.getStock();
        }else{
            return "失敗：商品「"+targetProduct.getName()+"」已售罄，無法購買！";
        }
    }
}
