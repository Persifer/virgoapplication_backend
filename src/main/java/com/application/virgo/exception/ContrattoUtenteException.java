package com.application.virgo.exception;

public class ContrattoUtenteException extends Exception{

    public ContrattoUtenteException(String messaggio, Throwable causa){
        super(messaggio, causa);
    }

    public ContrattoUtenteException(String messaggio){
        super(messaggio);
    }
}
