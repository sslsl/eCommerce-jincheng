package com.echo.exception;

public class LoginError extends RuntimeException{
    public LoginError(String message){
        super(message);
    }
    public LoginError(){
        super();
    }
}