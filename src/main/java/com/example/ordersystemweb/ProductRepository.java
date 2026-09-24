package com.example.ordersystemweb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //告訴Spring Boot這是最底層的資料庫倉儲組件
public interface ProductRepository extends JpaRepository<Product, String>{
    //不需要寫任何SQL指令。繼承JpaRepository後，這個介面就自動具備save(),findAll(),findById()等所有內建功能
}
