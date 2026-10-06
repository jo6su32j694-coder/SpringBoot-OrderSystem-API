package com.example.ordersystemweb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice //告訴Spring Boot這是全域異常攔截器
public class GlobalExceptionHandler {

    //專門攔截自訂的 BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException ex){
        //使用寫好的Result.error包裝格式
        Result<Void> errorResult = Result.error(ex.getMessage());

        //回傳包含狀態碼與 JSON Body 的回應
        return new ResponseEntity<>(errorResult,ex.getStatus());
    }
}
