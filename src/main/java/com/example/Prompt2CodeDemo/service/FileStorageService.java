package com.example.Prompt2CodeDemo.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    
    /**
     * Store a multipart file and return the relative path
     * @param file the file to store
     * @return the relative path of the stored file
     * @throws IOException if file storage fails
     */
    String storeFile(MultipartFile file) throws IOException;
    
    /**
     * Load file content as byte array
     * @param filePath the relative path of the file
     * @return file content as byte array
     * @throws IOException if file reading fails
     */
    byte[] loadFileAsBytes(String filePath) throws IOException;
    
    /**
     * Delete a file
     * @param filePath the relative path of the file to delete
     * @return true if file was deleted, false otherwise
     */
    boolean deleteFile(String filePath);
    
    /**
     * Check if file exists
     * @param filePath the relative path of the file
     * @return true if file exists, false otherwise
     */
    boolean fileExists(String filePath);
}