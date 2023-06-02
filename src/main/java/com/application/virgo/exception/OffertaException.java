package com.application.virgo.exception;

public class OffertaException extends Exception{

    public OffertaException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public OffertaException(String messaggio){
        super(messaggio);
    }
}
