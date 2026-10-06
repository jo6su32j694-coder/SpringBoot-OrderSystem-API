package com.example.ordersystemweb;

public class Result<T> {
    private boolean success; //是否成功
    private String message; //訊息提示
    private T data; //實際資料主體(泛型，可裝任何物件)

    //私有建構子，強迫使用下方靜態方法
    private Result(boolean success,String message,T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

    //快捷鍵方法：成功(不帶資料)
    public static <T> Result<T> success(String message){
        return new Result<>(true,message,null);
    }

    //快捷鍵方法：成功(帶有資料體，如商品、清單)
    public static <T> Result<T> success(String message,T data){
        return new Result<>(true,message,data);
    }

    //快捷鍵方法：失敗
    public static <T> Result<T> error(String message){
        return new Result<>(false,message,null);
    }

    //Getter & Setter (Spring Boot 轉成 JSON 格式必備)
    public boolean isSuccess() {return success;}
    public void setSuccess(boolean success) {this.success = success;}

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public T getData() {return data;}
    public void setData(T data) {this.data = data;}
}
