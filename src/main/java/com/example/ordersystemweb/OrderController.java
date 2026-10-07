package com.example.ordersystemweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/order-system")
public class OrderController {

    //依賴注入(Dependency Injection, DI)
    //透過Autowired，Spring Boot會自動把寫好的OrderService分配給Controller
    @Autowired
    private OrderService orderService;

    //使用 {productName} 作為動態路徑
    @GetMapping("/products/{productName}")
    public Result<Product> getProduct(@PathVariable String productName){
        //Spring Boot 會自動把網址中 {productName} 的位置，自動帶入到變數 productName 裡
        Product product = orderService.getProductByName(productName);
        //包裹後回傳：success:true, message=成功,data=商品物件
        return Result.success("成功獲取「"+productName+"」的資料",product);
    }

    @GetMapping("/item-list")
    public Result<List<Product>> getItemList(){
        List<Product> products = orderService.getAllProducts();
        //包裹後回傳：success=true,data=商品清單陣列
        return Result.success("成功獲取所有商品清單",products);
    }

    //修改後的購買 API:支援傳入數量參數(若不傳入預設為 1 )
    @PostMapping("/buy")
    public Result<String> buyItem(
            @RequestParam String itemName,
            @RequestParam (defaultValue = "1") int quantity){
        //將數量一起傳給 Service 處理
        String successMessage = orderService.processPurchase(itemName,quantity);
        //包裹後回傳：success=true,message=購買成功!...
        return Result.success(successMessage);
    }

    //監聽獲取歷史訂單的 Get 請求
    @GetMapping("/order-list")
    //修改後的訂單清單 API:支援分頁與排序
    //網址範例: /api/order-list?page=0&size=5
    public Result<Page<Order>> getOrderList(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "5")int size){
        //呼叫 Service 獲取分頁後的訂單資料
        Page<Order> orderPage = orderService.getAllOrders(page,size);
        return Result.success("成功獲取歷史訂單紀錄",orderPage);
    }
}
