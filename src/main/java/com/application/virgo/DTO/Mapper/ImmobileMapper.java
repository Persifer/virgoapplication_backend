package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.model.Immobile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Component
public class ImmobileMapper implements Function<Immobile, ImmobileDTO> {

    /*
    * Long idImmobile, Date dataUltimoRestauro, Date dataAcquisizione,
                    Date dataCreazioneImmobile, Float prezzo, Utente idProprietario
    * */

    @Override
    public ImmobileDTO apply(Immobile immobile) {

        ImmobileDTO immobileDto = new ImmobileDTO();

        immobileDto.setIdProprietario(immobile.getProprietario().getIdUtente() != null ? immobile.getProprietario().getIdUtente() : null);
        immobileDto.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : null);
        immobileDto.setDataUltimoRestauro(immobile.getDataUltimoRestauro() != null ? immobile.getDataUltimoRestauro() : null);
        immobileDto.setDataAcquisizione(immobile.getDataAcquisizione() != null ? immobile.getDataAcquisizione() : null);
        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : null);

        return immobileDto;
    }

    public Immobile apply(ImmobileDTO immobile) {

        Immobile newImmobile = new Immobile();

        newImmobile.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : null);
        newImmobile.setDataUltimoRestauro(immobile.getDataUltimoRestauro() != null ? immobile.getDataUltimoRestauro() : null);
        newImmobile.setDataAcquisizione(immobile.getDataAcquisizione() != null ? immobile.getDataAcquisizione() : null);
        newImmobile.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : null);

        return newImmobile;
    }



}