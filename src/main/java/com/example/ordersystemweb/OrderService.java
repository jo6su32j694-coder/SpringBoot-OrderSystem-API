package com.example.ordersystemweb;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service //關鍵註解：讓 Spring Boot 知道這是一個Service組件
public class OrderService {

    //把原本放在Controll的商品倉庫搬過來
    private final Map<String, Product> productInventory = new HashMap<>();

    public OrderService() {
        Product milkTea = new Product("奶茶", 65, 10);
        Product friedChicken = new Product("炸雞", 85, 5);
        Product sausage = new Product("香腸", 45, 7);

        productInventory.put(milkTea.getName(),milkTea);
        productInventory.put(friedChicken.getName(),friedChicken);
        productInventory.put(sausage.getName(),sausage);
    }

    //業務邏輯：取得單一商品
    public Product getProductByName(String name){
        return productInventory.get(name);
    }

    //業務邏輯：取得所有商品清單
    public List<Product> getAllProducts(){
        return new ArrayList<>(productInventory.values());
    }

    //業務邏輯：處理購買與扣庫存
    public String processPurchase(String itemName){
        Product targetProduct = productInventory.get(itemName);

        if (targetProduct == null){
            return "錯誤：找不到名為「"+itemName+"」的商品！";
        }

        if (targetProduct.getStock()>0){
            targetProduct.setStock(targetProduct.getStock()-1);
            return "購買成功！「"+targetProduct.getName()+"」已加入購物車，剩餘庫存： "+targetProduct.getStock();
        }else{
            return "失敗：商品「"+targetProduct.getName()+"」已售罄，無法購買！";
        }
    }

}
