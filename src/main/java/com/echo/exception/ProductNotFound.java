package com.echo.exception;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message){
        super(message);
    }
    public ProductNotFound(){
        super();
    }
}