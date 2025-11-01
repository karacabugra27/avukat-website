package com.avukatwebsite.backend.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String store(MultipartFile file, String subDirectory);

    void delete(String storedPath);
}
