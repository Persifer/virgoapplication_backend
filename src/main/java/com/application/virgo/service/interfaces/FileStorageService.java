/**
 * L'interfaccia FileStorageService fornisce metodi per gestire le operazioni di archiviazione dei file.
 */
package com.application.virgo.service.interfaces;

        import java.io.IOException;
        import java.nio.file.Path;
        import java.util.stream.Stream;

        import org.springframework.core.io.Resource;
        import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * Salva il file all'interno del pc con nome file del tipo:
     *  idUtente_idimmobile_nomeFile
     * @param file     Il file da salvare.
     * @param idUtente L'ID dell'utente.
     * @param nomeFile Il nome del file.
     * @return Una stringa che rappresenta l'identificatore del file salvato.
     */
    public String save(MultipartFile file, String idUtente, String nomeFile);

}