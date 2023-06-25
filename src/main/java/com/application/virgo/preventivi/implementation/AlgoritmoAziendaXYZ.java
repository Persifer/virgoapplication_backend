package com.application.virgo.preventivi.implementation;

import com.application.virgo.preventivi.interfaccia.AlgoritmoDiPreventivo;

public class AlgoritmoAziendaXYZ implements AlgoritmoDiPreventivo {

    @Override
    public Double calcolaPreventivo(Float prezzo, Integer metriQuadri, Integer locali) {
        Double test1 = Math.sqrt((locali+metriQuadri) * prezzo);
        Double test2 = test1 * prezzo;


        return Math.abs(Math.round(test2*100.d)/100.0d);
    }
}
