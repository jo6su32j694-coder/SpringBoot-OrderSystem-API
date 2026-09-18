package com.example.ordersystemweb;

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
    public Product getMilkTea(){
        //Controller不親自撈資料，轉手叫Service去拿
        return orderService.getProductByName("奶茶");
    }

    @GetMapping("/item-list")
    public List<Product> getItemList(){
        //Controller不親自組裝清單，由Service丟出來
        return orderService.getAllProducts();
    }

    @PostMapping("/buy")
    public String buyItem(@RequestParam String itemName){
        //Controller只負責收參數，核心扣庫存邏輯全部交給Service處理
        return orderService.processPurchase(itemName);
    }
}
