package com.max;

public class CantBookException extends Exception{

    public CantBookException(String message) {
        super(message);
    }

    public CantBookException() {
        super("Cannot book the requested time slot.");
    }
}
