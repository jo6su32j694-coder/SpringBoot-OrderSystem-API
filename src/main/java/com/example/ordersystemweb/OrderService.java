package com.example.ordersystemweb;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service //關鍵註解：讓 Spring Boot 知道這是一個Service組件
public class OrderService {

    @Autowired //注入資料庫倉儲層(Repository)
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

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
            //扣除庫存並更新資料表
            targetProduct.setStock(targetProduct.getStock()-1);
            productRepository.save(targetProduct);

            //建立訂單物件，並存入 orders 資料表中
            Order newOrder = new Order(targetProduct,1);//購買一個
            orderRepository.save(newOrder);//儲存訂單到資料庫

            return "購買成功！「"+targetProduct.getName()+"」已加入購物車，並生成訂單編號 #"+newOrder.getId();
        }else{
            return "失敗：商品「"+targetProduct.getName()+"」已售罄，無法購買！";
        }
    }

    //獲取所有歷史訂單紀錄
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
