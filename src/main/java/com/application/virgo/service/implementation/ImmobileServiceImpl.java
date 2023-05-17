package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.ImmobileInformationMapper;
import com.application.virgo.DTO.Mapper.ImmobileMapper;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.ImmobileJpaRepository;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImmobileServiceImpl implements ImmobileService {

    //TODO -> Implementa pagination all'interno di get all immobili


    private final ImmobileJpaRepository immobileRepo;
    private final ImmobileMapper mapperImmobile;
    private final UtenteService utenteService;
    private final ImmobileInformationMapper mapperInformation;

    @Autowired
    public ImmobileServiceImpl(ImmobileJpaRepository immobileRepo, ImmobileMapper mapperImmobile, UtenteService utenteService, ImmobileInformationMapper mapperInformation){
        this.immobileRepo = immobileRepo;
        this.mapperImmobile = mapperImmobile;
        this.utenteService = utenteService;
        this.mapperInformation = mapperInformation;
    }

    @Override
    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile)
        throws ImmobileException, UtenteException {
        // Prelevo l'utente dal db
        // TODO: Cambia prendendo i dati dell'utente dalla sessione di spring, COSI NON HA SENSO
        Optional<Utente> utenteProprietario = utenteService.getUtenteClassById(tempNewImmobile.getIdProprietario());
        LocalDate todayDate = LocalDate.now();
        if(utenteProprietario.isPresent()){
            //Controllo che le data di acquisizione sia minore di quella odierna
            if(tempNewImmobile.getDataAcquisizione().toLocalDate().isBefore(todayDate)){
                //Controllo che le data di ultimo restuaro sia minore di quella odierna, può capitare che un restauro sia ancora in corso
                // ma meglio evitare
                if(tempNewImmobile.getDataUltimoRestauro().toLocalDate().isBefore(todayDate)){
                    // Inserisco la data di inserimento
                    tempNewImmobile.setDataCreazioneImmobile(Date.valueOf(todayDate));
                    // Conversione da ImmobileDTO a Immobile
                    Immobile newImmobile = mapperImmobile.apply(tempNewImmobile);
                    newImmobile.setProprietario(utenteProprietario.get());
                    // Salvataggio dell'immobile
                    immobileRepo.save(newImmobile);
                    return Optional.of(tempNewImmobile);
                }else{
                    throw new ImmobileException("La data di ultimo restauro immobile è superiore a quella odierna!");
                }

            }else{
                throw new ImmobileException("La data di acqusizione immobile è superiore a quella odierna!");
            }

        }else{
            throw new UtenteException("Utente proprietario non trovato");
        }

    }

    @Override
    public Optional<GetImmobileInfoDTO> getImmobileById(String idImmobile) throws ImmobileException{
        Long convertedIdImmobile = Long.parseLong(idImmobile);

        Optional<Immobile> tempImmobile = immobileRepo.getImmobilesByIdImmobile(convertedIdImmobile);
        if(tempImmobile.isPresent()){
            Immobile requestedImmobile = tempImmobile.get();

            return Optional.of(new GetImmobileInfoDTO(
                    requestedImmobile.getProprietario().getNome(),
                    requestedImmobile.getProprietario().getCognome(),
                    requestedImmobile.getDataUltimoRestauro(),
                    requestedImmobile.getDataAcquisizione(),
                    requestedImmobile.getDataCreazioneImmobile(),
                    requestedImmobile.getDescrizione(),
                    requestedImmobile.getPrezzo()
            ));
        }else{
            throw new ImmobileException("L'immobile cercato non esiste!");
        }


    }

    @Override
    public Optional<Immobile> updateImmobileInformation(ImmobileDTO tempUpdatedImmobile) throws ImmobileException {
        return Optional.empty();
    }

    @Override
    public List<GetImmobileInfoDTO> getAllImmobiliPaginated(Long inidiceIniziale, Long pageSize) throws ImmobileException{

        if(inidiceIniziale > immobileRepo.countByIdImmobile() - pageSize){
            if(pageSize > 20){
                Page<Immobile> listImmobili = immobileRepo.findAll(PageRequest.of(inidiceIniziale.intValue(), pageSize.intValue()));
                // converte con la stream una page di immobili in una lista di getImmobileInfoDTO
                return listImmobili.stream().map(mapperInformation).collect(Collectors.toList());
            }else {
                throw new ImmobileException("Attenzione il numero dell'elemento da cui partire è troppo alto");
            }
        }else{
            throw new ImmobileException("Attenzione il numero dell'elemento da cui partire è troppo alto");
        }
    }

    @Override
    public List<GetImmobileInfoDTO> getFilteredImmobiliPaginated(String filter) {
        return null;
    }

    @Override
    public List<GetImmobileInfoDTO> getImmobiliByKeyword(String keyword) {
        return null;
    }
}
