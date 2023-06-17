package com.application.virgo.exception;

public class ContrattoException extends Exception{

    public ContrattoException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public ContrattoException(String messaggio){
        super(messaggio);
    }
}
