package com.example.ordersystemweb;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order-system") //點餐系統網址前綴
public class OrderController {

    //核心改動：用一個Map統一管理所有商品。key是商品名稱，value是商品物件。
    private final Map<String,Products> productInventory = new HashMap<>();

    public OrderController() {
        Products milkTea = new Products("奶茶", 65, 10);
        Products friedChicken = new Products("炸雞", 85, 5);
        Products sausage = new Products("香腸", 45, 7);

        //把商品「有名有姓」的存進Map倉庫
        productInventory.put(milkTea.getName(),milkTea);
        productInventory.put(friedChicken.getName(),friedChicken);
        productInventory.put(sausage.getName(),sausage);
    }

    // 1.監聽 "/api/order-system/products/milk-tea "(保留原本的路徑，改從Map撈取)
    @GetMapping("/products/milk-tea")
    public Products getMilkTea(){
        return productInventory.get("奶茶");
    }

    // 2.監聽 "/api/order-system/item-list"(直接把Map裡的所有Value轉成List丟出去)
    @GetMapping("/item-list")
    public List<Products> getItemList(){
        return new ArrayList<>(productInventory.values());
    }

    //處理點單購買的POST API
    @PostMapping("/buy")
    public String buyItem(@RequestParam String itemName){

        //直接去Map倉庫裡用「名字」領取商品物件。有就有，沒有就是null。
        Products targetProduct = productInventory.get(itemName);

        if (targetProduct == null){
            return "錯誤：找不到名為「"+itemName+"」的商品！";
        }

        if (targetProduct.getStock()>0){
            targetProduct.setStock(targetProduct.getStock()-1);//扣除1個庫存
            return "購買成功！「"+targetProduct.getName()+"」已加入購物車，剩餘庫存： "+targetProduct.getStock();
        }else{
            return "失敗：商品「"+targetProduct.getName()+"」已售罄，無法購買！";
        }
    }
}
