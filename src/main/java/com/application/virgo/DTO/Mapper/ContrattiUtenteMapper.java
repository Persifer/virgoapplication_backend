package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.ContrattiUtenteDTO;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.application.virgo.utilities.Constants.FORMATTER;

@Component
public class ContrattiUtenteMapper implements Function<ContrattoUtente, ContrattiUtenteDTO> {
    @Override
    public ContrattiUtenteDTO apply(ContrattoUtente contrattoUtente) {
        ContrattiUtenteDTO contrattiUtenteDTO = new ContrattiUtenteDTO();

        contrattiUtenteDTO.setNomeAcquirente(contrattoUtente.getAcquirente().getNome());
        contrattiUtenteDTO.setCognomeAcquirente(contrattoUtente.getAcquirente().getCognome());

        contrattiUtenteDTO.setDataStipulazione(contrattoUtente.getContrattoInteressato().getDataStipulazione() != null ?
                FORMATTER.format(contrattoUtente.getContrattoInteressato().getDataStipulazione()) : "");

        contrattiUtenteDTO.setIdImmobile(contrattoUtente.getContrattoInteressato().getImmobileInteressato().getIdImmobile().toString());

        contrattiUtenteDTO.setIdContratto(contrattoUtente.getIdContrattoUtente().getIdContratto());

        return contrattiUtenteDTO;
    }
}
