package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ImmobileService {

// ======================================== HOME PAGE =================================================================

    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile, Utente proprietario)
            throws ImmobileException, UtenteException;

    public Optional<GetImmobileInfoDTO> getImmobileById(Long idImmobile) throws ImmobileException;

    public Optional<ImmobileDTO> getImmobileByIdToUpdate(Long idImmobile, Utente proprietario)
            throws ImmobileException, UtenteException;

    public Optional<Immobile> updateImmobileInformation(ImmobileDTO tempUpdatedImmobile, Utente proprietario, Long idImmobileToUpdate)
            throws ImmobileException, UtenteException;

    public List<GetImmobileInfoDTO> getAllImmobiliPaginated(Long inidiceIniziale, Long pageSize) throws ImmobileException;


    public List<GetImmobileInfoDTO> getFilteredImmobiliPaginated(String filter);

    public List<GetImmobileInfoDTO> getImmobiliByKeyword(String keyword);

//    ==================================================================================================================

// ======================================== HOME PAGE ==================================================================

//    public List<>
    

}
