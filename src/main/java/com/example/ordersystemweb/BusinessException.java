package com.example.ordersystemweb;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{
    private final HttpStatus status; //記錄這個錯誤要回傳什麼 Http 狀態碼

    public BusinessException(String message,HttpStatus status){
        super(message);
        this.status=status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
