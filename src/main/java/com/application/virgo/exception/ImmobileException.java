package com.application.virgo.exception;

public class ImmobileException extends Exception{

    public ImmobileException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public ImmobileException(String messaggio){
        super(messaggio);
    }
}
