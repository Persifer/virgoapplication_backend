package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ImmobileService {

    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile) throws ImmobileException, UtenteException;

    public Optional<GetImmobileInfoDTO> getImmobileById(String idImmobile) throws ImmobileException;

    public Optional<Immobile> updateImmobileInformation(ImmobileDTO tempUpdatedImmobile) throws ImmobileException;

    public List<GetImmobileInfoDTO> getAllImmobiliPaginated(Long inidiceIniziale, Long pageSize) throws ImmobileException;

    public List<GetImmobileInfoDTO> getFilteredImmobiliPaginated(String filter);

    public List<GetImmobileInfoDTO> getImmobiliByKeyword(String keyword);
}
