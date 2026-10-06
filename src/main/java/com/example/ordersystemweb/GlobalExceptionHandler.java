package com.example.ordersystemweb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //告訴Spring Boot這是全域異常攔截器
public class GlobalExceptionHandler {

    //專門攔截自訂的 BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,Object>> handleBusinessException(BusinessException ex){
        Map<String,Object> errorBody=new HashMap<>();
        errorBody.put("status",ex.getStatus().value()); //狀態碼數字，例如：404
        errorBody.put("error",ex.getStatus().getReasonPhrase()); //狀態碼錯誤名稱，例如：Not Found
        errorBody.put("message",ex.getMessage()); // 在 Service 寫的中文錯誤訊息

        //回傳包含狀態碼與 JSON Body 的回應
        return new ResponseEntity<>(errorBody,ex.getStatus());
    }
}
