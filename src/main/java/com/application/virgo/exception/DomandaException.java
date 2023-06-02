package com.application.virgo.exception;

public class DomandaException extends Exception{

    public DomandaException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public DomandaException(String messaggio){
        super(messaggio);
    }
}

