package org.example.service;

import java.util.List;

public interface FileService {
    List<String> readFromFile(String filePath);

    void addData(String filePath, String data);
}
