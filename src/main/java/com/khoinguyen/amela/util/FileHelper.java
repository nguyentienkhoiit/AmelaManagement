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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.khoinguyen.amela.util.Constant.ALLOWED_EXTENSIONS;
import static com.khoinguyen.amela.util.Constant.UPLOAD_DIR;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileHelper {

    public String uploadFile(MultipartFile file, User user) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) return null;

        String newFileName = AttributeGenerator.generatorUsername(user, user.getId()) + ".jpg";

        Path uploadPath = Paths.get(Constant.UPLOAD_DIR);

        try (InputStream inputStream = file.getInputStream()) {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(inputStream, filePath, REPLACE_EXISTING);
        } catch (MaxUploadSizeExceededException e) {
            log.error("Max upload size exceeded: {}", e.getMessage());
            return null;
        } catch (IOException e) {
            log.error("Failed to upload file: {}", e.getMessage());
            return null;
        }
        return newFileName;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public Resource load(String filename) {
        Path root = Paths.get(UPLOAD_DIR);
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("Could not read the file!");
                return null;
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            return null;
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
        Set<String> bannedWords;
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
