package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;

import java.util.List;
import java.util.Optional;

public interface ImmobileService {

    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile) throws ImmobileException, UtenteException;

    public Optional<GetImmobileInfoDTO> getImmobileById(String idImmobile) throws ImmobileException;

    public Optional<Immobile> updateImmobileInformation(ImmobileDTO tempUpdatedImmobile) throws ImmobileException;

    public List<Immobile> getAllImmobili();

    public List<Immobile> getFilteredImmobili(String filter);

    public List<Immobile> getImmobiliByKeyword(String keyword);
}
