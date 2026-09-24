package com.example.ordersystemweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service //關鍵註解：讓 Spring Boot 知道這是一個Service組件
public class OrderService {

    @Autowired //注入資料庫倉儲層(Repository)
    private ProductRepository productRepository;

    //@PostConstruct註解，當Service初始化完成後，會自動執行這個方法
    //如果發現資料庫是全空的，就把if內的資料塞進MySQL裡
    @PostConstruct
    public void initDatabaseInventory(){
        if(productRepository.count() == 0){
            System.out.println("偵測到資料庫為空，開始初始化預設商品資料...");
            productRepository.save(new Product("奶茶",65,10));
            productRepository.save(new Product("炸雞排",85,5));
            productRepository.save(new Product("香腸",45,7));
            System.out.println("預設商品資料初始化成功！");
        }
    }

    //業務邏輯：取得單一商品
    public Product getProductByName(String name){
        //findById 會回傳一個Optional,如果找不到就回傳null
        return productRepository.findById(name).orElse(null);
    }

    //業務邏輯：取得所有商品清單
    public List<Product> getAllProducts(){
        //findAll 直接撈出Product資料庫的所有資料
        return productRepository.findAll();
    }

    //業務邏輯：處理購買與扣除資料庫庫存
    public String processPurchase(String itemName){
        //1.去資料庫找有沒有這個商品
        Product targetProduct = productRepository.findById(itemName).orElse(null);

        if (targetProduct == null){
            return "錯誤：找不到名為「"+itemName+"」的商品！";
        }

        if (targetProduct.getStock()>0){
            targetProduct.setStock(targetProduct.getStock()-1);

            //將扣除後的結果「存回」資料庫中更新
            productRepository.save(targetProduct);

            return "購買成功！「"+targetProduct.getName()+"」已加入購物車，剩餘庫存： "+targetProduct.getStock();
        }else{
            return "失敗：商品「"+targetProduct.getName()+"」已售罄，無法購買！";
        }
    }

}
