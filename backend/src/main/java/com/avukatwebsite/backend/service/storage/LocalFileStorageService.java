package com.avukatwebsite.backend.service.storage;

import com.avukatwebsite.backend.exception.BusinessException;
import com.avukatwebsite.backend.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocalFileStorageService implements FileStorageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
        "image/jpeg",
        "image/png",
        "image/webp",
        "image/gif"
    );

    @Value("${app.storage.root-directory}")
    private String rootDirectory;

    @Value("${app.storage.public-prefix:/files}")
    private String publicPrefix;

    @Override
    public String store(MultipartFile file, String subDirectory) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorType.VALIDATION_FAILED, "Yüklenecek dosya bulunamadı");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new BusinessException(ErrorType.VALIDATION_FAILED, "Desteklenmeyen dosya tipi: " + contentType);
        }

        String extension = resolveExtension(file.getOriginalFilename(), contentType);
        String filename = UUID.randomUUID() + "." + extension;

        Path targetDirectory = resolveDirectory(subDirectory);
        Path targetFile = targetDirectory.resolve(filename);

        try {
            Files.createDirectories(targetDirectory);
            file.transferTo(targetFile);
            log.info("Dosya kaydedildi: {}", targetFile);
        } catch (IOException ex) {
            throw new BusinessException(ErrorType.GENERIC_BUSINESS_ERROR, "Dosya kaydedilemedi: " + ex.getMessage());
        }

        return buildPublicPath(subDirectory, filename);
    }

    @Override
    public void delete(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) {
            return;
        }

        String relativePath = extractRelativePath(storedPath);
        Path absolutePath = Paths.get(rootDirectory).resolve(relativePath);

        try {
            Files.deleteIfExists(absolutePath);
            log.info("Dosya silindi: {}", absolutePath);
        } catch (IOException ex) {
            log.warn("Dosya silinemedi: {} - {}", absolutePath, ex.getMessage());
        }
    }

    private Path resolveDirectory(String subDirectory) {
        Path root = Paths.get(rootDirectory);
        if (subDirectory == null || subDirectory.isBlank()) {
            return root;
        }
        return root.resolve(subDirectory);
    }

    private String buildPublicPath(String subDirectory, String filename) {
        String normalizedPrefix = publicPrefix.startsWith("/") ? publicPrefix : "/" + publicPrefix;
        if (normalizedPrefix.endsWith("/")) {
            normalizedPrefix = normalizedPrefix.substring(0, normalizedPrefix.length() - 1);
        }

        StringBuilder builder = new StringBuilder(normalizedPrefix);
        if (subDirectory != null && !subDirectory.isBlank()) {
            builder.append("/").append(subDirectory);
        }
        builder.append("/").append(filename);
        return builder.toString();
    }

    private String extractRelativePath(String storedPath) {
        String normalizedPrefix = publicPrefix.startsWith("/") ? publicPrefix : "/" + publicPrefix;
        if (!storedPath.startsWith(normalizedPrefix)) {
            return storedPath.startsWith("/") ? storedPath.substring(1) : storedPath;
        }
        String relative = storedPath.substring(normalizedPrefix.length());
        if (relative.startsWith("/")) {
            relative = relative.substring(1);
        }
        return relative;
    }

    private String resolveExtension(String originalFilename, String contentType) {
        if (originalFilename != null && originalFilename.contains(".")) {
            String ext = StringUtils.getFilenameExtension(originalFilename);
            if (ext != null && !ext.isBlank()) {
                return ext.toLowerCase();
            }
        }
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/webp" -> "webp";
            case "image/gif" -> "gif";
            default -> "bin";
        };
    }
}
