package com.avukatwebsite.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageConfig implements WebMvcConfigurer {

    @Value("${app.storage.root-directory}")
    private String rootDirectory;

    @Value("${app.storage.public-prefix:/files}")
    private String publicPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String prefix = normalizePublicPrefix();
        String pattern = prefix.endsWith("/**") ? prefix : prefix + "/**";
        String location = "file:" + resolveRootDirectory();

        registry.addResourceHandler(pattern)
            .addResourceLocations(location);
    }

    private String normalizePublicPrefix() {
        String prefix = publicPrefix.startsWith("/") ? publicPrefix : "/" + publicPrefix;
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        return prefix;
    }

    private String resolveRootDirectory() {
        Path path = Paths.get(rootDirectory).toAbsolutePath();
        String directory = path.toString();
        if (!directory.endsWith("/")) {
            directory = directory + "/";
        }
        return directory;
    }
}
