package com.echo.exception;

public class CustomerNotFound extends RuntimeException{
    public CustomerNotFound(String message){
        super(message);
    }
    public CustomerNotFound(){
        super();
    }
}
