package com.example.ordersystemweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
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
    //新增數量參數
    public String processPurchase(String itemName, int quantity){
        //防禦性程式設計：購買數量不能少於 1
        if(quantity < 1){
            throw new BusinessException("購買數量必須大於 0",HttpStatus.BAD_REQUEST);
        }
        Product targetProduct = productRepository.findById(itemName).orElse(null);

        //1.找不到商品 -> 拋出 404 Not found
        if (targetProduct == null){
             throw new BusinessException("錯誤：找不到名為「"+itemName+"」的商品！",HttpStatus.NOT_FOUND);
        }

        //2.庫存不足 -> 拋出 400 Bad Request
        //檢查庫存是否滿足本次購買數量
        if (targetProduct.getStock() >= quantity){
            //扣除對應的數量並更新資料表
            targetProduct.setStock(targetProduct.getStock()-quantity);
            productRepository.save(targetProduct);

            //建立訂單物件，並存入 orders 資料表中
            Order newOrder = new Order(targetProduct,quantity);//傳入動態數量，內部會自動計算totalPrice
            orderRepository.save(newOrder);//儲存訂單到資料庫

            return "購買成功！「"+targetProduct.getName()+"」×"+quantity+"已加入購物車，並生成訂單編號 #"+newOrder.getId();
        }else{
            throw new BusinessException("商品「"+targetProduct.getName()+"」庫存不足！目前剩餘庫存："+targetProduct.getStock()+"，您預計購買："+quantity,HttpStatus.BAD_REQUEST);
        }
    }

    //獲取所有歷史訂單紀錄
    //分頁與排序業務邏輯
    public Page<Order> getAllOrders(int page,int size){
        //設定分頁參數：第 page 頁(從 0 開始算)、每頁 size 筆，並依造id進行降序(descending)排列，讓最新的訂單在最前面
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        //JpaRepository 內建支援傳入 Pageable, 會自動去 MySQL 執行 LIMIT 和 OFFSET
        return orderRepository.findAll(pageable);
    }
}
