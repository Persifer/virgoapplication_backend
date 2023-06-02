package com.application.virgo.exception;

public class OffertaUtenteException extends Exception{

    public OffertaUtenteException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public OffertaUtenteException(String messaggio){
        super(messaggio);
    }
}

