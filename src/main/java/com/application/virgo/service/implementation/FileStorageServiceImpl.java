package com.application.virgo.service.implementation;

import com.application.virgo.service.interfaces.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor

public class FileStorageServiceImpl implements FileStorageService {

    private final Path root = Paths.get("C:\\progetto_ing_sw\\src\\main\\resources\\static");

    private Path setPathUtente(String idUtente){
        return Path.of(root+"\\"+idUtente);
    }

    /**
     * Salva il file all'interno del pc con nome file del tipo:
     *  idUtente_idimmobile_nomeFile
     * @param file     Il file da salvare.
     * @param idUtente L'ID dell'utente.
     * @param nomeFile Il nome del file.
     * @return Una stringa che rappresenta l'identificatore del file salvato.
     */
    @Override
    public String save(MultipartFile file, String idUtente, String nomeFile) {
        try {
            // setto il path dove salvare i file

            Path pathForUtente = setPathUtente(idUtente);

            // se non esiste la directory la creo
            if(!Files.isDirectory(pathForUtente)){
                Files.createDirectory(pathForUtente);
            }

            // salvo il file
            Files.copy(file.getInputStream(), Path.of(pathForUtente+"\\"+nomeFile));

            // resituisco l'id utente
            return idUtente;

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

}
