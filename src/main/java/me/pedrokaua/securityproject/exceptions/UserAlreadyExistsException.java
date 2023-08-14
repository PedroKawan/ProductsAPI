package me.pedrokaua.securityproject.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    private String message;
    private Throwable cause;

    public UserAlreadyExistsException(){
        this.message = "User Already Exists!";
    }

    public UserAlreadyExistsException(String message){
        this.message = message;
    }

    public UserAlreadyExistsException(Throwable throwable){
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
