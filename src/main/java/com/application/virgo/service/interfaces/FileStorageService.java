package com.application.virgo.service.interfaces;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public void init();

    public String save(MultipartFile file, String idUtente, String nomeFile);

    public Resource load(String filename, String idUtente);

    public void deleteAll(String idUtente) throws IOException;

    public Stream<Path> loadAll(String idUtente);
}