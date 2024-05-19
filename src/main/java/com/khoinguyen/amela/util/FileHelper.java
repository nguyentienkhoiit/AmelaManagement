package com.khoinguyen.amela.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoinguyen.amela.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileHelper {

    public String uploadFile(MultipartFile file, User user) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

//        String fileExtension = getFileExtension(fileName);
        String newFileName = AttributeGenerator.generatorUsername(user, user.getId()) + ".jpg";

        String uploadDir = "./upload";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, REPLACE_EXISTING);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        return newFileName;
    }

    private String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i > 0 ? fileName.substring(i + 1) : "";
    }

    public Resource load(String filename) {
        Path root = Paths.get("./upload");
        try {
            Path file = root.resolve(filename);
            Resource resource = (Resource) new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Set<String> readFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("file/backList.json");
        if (inputStream == null) return null;
        return objectMapper.readValue(inputStream, new TypeReference<Set<String>>() {
        });
    }

    public Set<String> getListBannedWord(String paragraph) {
        Set<String> bannedWords = new HashSet<>();
        try {
            bannedWords = readFile();
            if (bannedWords.isEmpty()) return null;
        } catch (IOException e) {
            log.error("exception: {}", e.getMessage());
            return null;
        }
        return bannedWords.stream()
                .filter(w -> paragraph.toLowerCase().contains(w.toLowerCase()))
                .collect(Collectors.toSet());
    }
}