package com.application.virgo.exception;

public class RispostaException extends Exception{

    public RispostaException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public RispostaException(String messaggio){
        super(messaggio);
    }
}
