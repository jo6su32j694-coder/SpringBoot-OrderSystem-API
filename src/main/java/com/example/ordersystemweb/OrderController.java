package com.example.ordersystemweb;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/order-system")
public class OrderController {

    //依賴注入(Dependency Injection, DI)
    //透過Autowired，Spring Boot會自動把寫好的OrderService分配給Controller
    @Autowired
    private OrderService orderService;

    @GetMapping("/products/milk-tea")
    public Result<Product> getMilkTea(){
        Product product = orderService.getProductByName("奶茶");
        //包裹後回傳：success:true, message=成功,data=商品物件
        return Result.success("成功獲取奶茶資料",product);
    }

    @GetMapping("/item-list")
    public Result<List<Product>> getItemList(){
        List<Product> products = orderService.getAllProducts();
        //包裹後回傳：success=true,data=商品清單陣列
        return Result.success("成功獲取所有商品清單",products);
    }

    @PostMapping("/buy")
    public Result<String> buyItem(@RequestParam String itemName){
        String successMessage = orderService.processPurchase(itemName);
        //包裹後回傳：success=true,message=購買成功!...
        return Result.success(successMessage);
    }

    //監聽獲取歷史訂單的 Get 請求
    @GetMapping("/order-list")
    public Result<List<Order>> getOrderList(){
        List<Order> orders = orderService.getAllOrders();
        return Result.success("成功獲取歷史訂單紀錄",orders);
    }
}
