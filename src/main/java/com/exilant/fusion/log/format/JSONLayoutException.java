package com.exilant.fusion.log.format;

public class JSONLayoutException extends RuntimeException{

    public JSONLayoutException(String message, Throwable t){
        super(message,t);
    }

    public JSONLayoutException(Throwable t){
        super(t);
    }
}
