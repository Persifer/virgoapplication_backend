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

    /**
     * Carica una foto dato il nome del file e l'idUtente
     *
     * @param filename Il nome del file da caricare.
     * @param idUtente L'ID dell'utente.
     * @return La risorsa che rappresenta il file caricato.
     */
    public Resource load(String filename, String idUtente);

    /**
     * Cancella tutti i file associati ad un determianto utente.
     *
     * @param idUtente L'ID dell'utente.
     * @throws IOException se si verifica un errore di I/O durante il processo di eliminazione.
     */
    public void deleteAll(String idUtente) throws IOException;

    /**
     * Cancella tutti i file associati ad un determianto utente
     *
     * @param idUtente L'ID dell'utente.
     * @return Uno stream di percorsi che rappresentano i file caricati.
     */
    public Stream<Path> loadAll(String idUtente);
}