package com.example.Prompt2CodeDemo.service.impl;

import com.example.Prompt2CodeDemo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    
    @Value("${app.file.upload-dir:./uploads}")
    private String uploadDir;
    
    @Value("${app.file.max-size:10485760}") // 10MB default
    private long maxFileSize;
    
    private static final String[] ALLOWED_EXTENSIONS = {".xlsx", ".xls"};
    private static final String[] ALLOWED_CONTENT_TYPES = {
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.ms-excel"
    };
    
    @Override
    public String storeFile(MultipartFile file) throws IOException {
        validateFile(file);
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = generateUniqueFilename(fileExtension);
        
        // Create subdirectory by date for better organization
        String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Path datePath = uploadPath.resolve(dateFolder);
        Files.createDirectories(datePath);
        
        // Store file
        Path targetLocation = datePath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        // Return relative path from upload directory
        return uploadPath.relativize(targetLocation).toString().replace("\\", "/");
    }
    
    @Override
    public byte[] loadFileAsBytes(String filePath) throws IOException {
        Path fullPath = Paths.get(uploadDir, filePath);
        return Files.readAllBytes(fullPath);
    }
    
    @Override
    public boolean deleteFile(String filePath) {
        try {
            Path fullPath = Paths.get(uploadDir, filePath);
            return Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public boolean fileExists(String filePath) {
        Path fullPath = Paths.get(uploadDir, filePath);
        return Files.exists(fullPath);
    }
    
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size of " + maxFileSize + " bytes");
        }
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }
        
        // Validate file extension
        String fileExtension = getFileExtension(originalFilename).toLowerCase();
        boolean validExtension = false;
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (fileExtension.equals(allowedExt)) {
                validExtension = true;
                break;
            }
        }
        
        if (!validExtension) {
            throw new IllegalArgumentException("File type not allowed. Only XLSX and XLS files are permitted");
        }
        
        // Validate content type
        String contentType = file.getContentType();
        if (contentType != null) {
            boolean validContentType = false;
            for (String allowedType : ALLOWED_CONTENT_TYPES) {
                if (contentType.equals(allowedType)) {
                    validContentType = true;
                    break;
                }
            }
            if (!validContentType) {
                throw new IllegalArgumentException("Content type not allowed. Only Excel files are permitted");
            }
        }
    }
    
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
    
    private String generateUniqueFilename(String fileExtension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("report_%s_%s%s", timestamp, uuid, fileExtension);
    }
}

