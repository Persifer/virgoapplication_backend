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

    @Override
    public String save(MultipartFile file, String idUtente, String nomeFile) {
        try {
            Path pathForUtente = setPathUtente(idUtente);

            if(!Files.isDirectory(pathForUtente)){
                Files.createDirectory(pathForUtente);
            }

            Files.copy(file.getInputStream(), Path.of(pathForUtente+"\\"+nomeFile));

            return idUtente;

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename, String idUtente) {
        try {

            Path imageFilePath = setPathUtente(idUtente).resolve(filename);
            Resource resource = new UrlResource(imageFilePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll(String idUtente) throws IOException {
        FileSystemUtils.deleteRecursively(setPathUtente(idUtente));
    }

    @Override
    public Stream<Path> loadAll(String idUtente) {
        try {
            Path pathForUtente = setPathUtente(idUtente);
            return Files.walk(pathForUtente, 1).filter(path -> !path.equals(pathForUtente)).map(pathForUtente::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
