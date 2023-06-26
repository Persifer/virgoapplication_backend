package com.application.virgo.exception;

public class PreventivoException extends Exception{

    public PreventivoException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public PreventivoException(String messaggio){
        super(messaggio);
    }
}