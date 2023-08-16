package me.pedrokaua.securityproject.exceptions;

public class ProductAlreadyRegisteredException extends RuntimeException{

    private String message;
    private Throwable cause;

    public ProductAlreadyRegisteredException(){
        this.message = "Product Already Registered!";
    }

    public ProductAlreadyRegisteredException(String message){
        this.message = message;
    }

    public ProductAlreadyRegisteredException(Throwable throwable){
        this.cause = throwable;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
