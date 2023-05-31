package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
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

        immobileDto.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : "");
        immobileDto.setDataUltimoRestauro(immobile.getDataUltimoRestauro() != null ? immobile.getDataUltimoRestauro() : null);
        immobileDto.setDataAcquisizione(immobile.getDataAcquisizione() != null ? immobile.getDataAcquisizione() : null);

        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : 0.0f);
        immobileDto.setLocali(immobile.getLocali() != null ? immobile.getLocali() : "");
        immobileDto.setMetriQuadri(immobile.getMetriQuadri() != null ? immobile.getMetriQuadri() : "");

        immobileDto.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        immobileDto.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        immobileDto.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        immobileDto.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");

        return immobileDto;
    }

    public Immobile apply(ImmobileDTO immobile) {

        Immobile newImmobile = new Immobile();

        newImmobile.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : "");
        newImmobile.setDataUltimoRestauro(immobile.getDataUltimoRestauro() != null ? immobile.getDataUltimoRestauro() : null);
        newImmobile.setDataAcquisizione(immobile.getDataAcquisizione() != null ? immobile.getDataAcquisizione() : null);

        newImmobile.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : 0.0f);

        newImmobile.setLocali(immobile.getLocali() != null ? immobile.getLocali() : "");
        newImmobile.setMetriQuadri(immobile.getMetriQuadri() != null ? immobile.getMetriQuadri() : "");

        newImmobile.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        newImmobile.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        newImmobile.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        newImmobile.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");

        return newImmobile;
    }



}